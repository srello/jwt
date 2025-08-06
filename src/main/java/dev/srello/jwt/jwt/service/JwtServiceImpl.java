package dev.srello.jwt.jwt.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.crypto.Ed25519Verifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.jwt.core.exception.RequestException;
import dev.srello.jwt.jwt.enums.JwtValidity;
import dev.srello.jwt.token.dto.TokenODTO;
import dev.srello.jwt.token.enums.TokenType;
import dev.srello.jwt.token.service.TokenService;
import dev.srello.jwt.user.dto.UserODTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.nimbusds.jose.JWSAlgorithm.EdDSA;
import static com.nimbusds.jose.jwk.Curve.Ed25519;
import static com.nimbusds.jose.jwk.KeyUse.SIGNATURE;
import static dev.srello.jwt.core.messages.Messages.Error.TOKEN_INVALID;
import static dev.srello.jwt.core.messages.Messages.Error.TOKEN_NOT_PARSEABLE;
import static dev.srello.jwt.jwt.enums.JwtValidity.*;
import static dev.srello.jwt.shared.process.HashUtils.hashString;
import static dev.srello.jwt.token.enums.TokenType.ANONYMOUS;
import static dev.srello.jwt.token.enums.TokenType.REFRESH;
import static java.lang.Integer.valueOf;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Map.copyOf;
import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    public static final String USER_CLAIMS = "userClaims";
    public static final String USERNAME_CLAIM = "username";
    public static final String ROLE_CLAIM = "role";
    public static final String TOKEN_TYPE_CLAIM = "token-type";
    @Value("${app.auth.jwtExpiration:1800}")
    private int jwtExpiration;
    @Value("${app.auth.issuerUrl:srello.dev}")
    private String issuerUrl;

    @Value("${app.auth.audienceUrl:srello.dev}")
    private String audienceUrl;

    private final TokenService tokenService;
    private static final Clock clock = Clock.systemUTC();

    @Override
    public SignedJWT generateToken(UserODTO userODTO, TokenType tokenType) throws JOSEException, ParseException {

        OctetKeyPair jwk = generateOctetKeyPair();
        OctetKeyPair publicJWK = jwk.toPublicJWK();

        Map<String, String> claims = getClaims(userODTO, tokenType);
        Date now = new Date();
        Date expirationTime = new Date(now.toInstant().plusSeconds(jwtExpiration).toEpochMilli());

        if (tokenType.equalsAny(REFRESH, ANONYMOUS)) {
            expirationTime = new Date(expirationTime.toInstant().plus(30, DAYS).toEpochMilli());
        }

        SignedJWT signedJWT = generateSignedJWT(userODTO, jwk, now, claims, expirationTime);
        JWSVerifier verifier = new Ed25519Verifier(publicJWK);

        tokenService.createToken(jwk, userODTO, tokenType, now, expirationTime, signedJWT);

        checkArgument(signedJWT.verify(verifier));
        checkArgument(new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()));
        checkArgument(signedJWT.getJWTClaimsSet().getIssuer().equals(issuerUrl));

        return signedJWT;
    }

    @Override
    public SignedJWT getDecodedJwt(String jwt) {
        try {
            if (jwt.matches("^Bearer .*"))
                return SignedJWT.parse(jwt.split("Bearer ")[1]);
            return SignedJWT.parse(jwt);
        } catch (ParseException ex) {
            throw new RequestException(BAD_REQUEST, TOKEN_NOT_PARSEABLE, jwt);
        }
    }

    @Override
    public synchronized JwtValidity verifyJwt(SignedJWT signedJWT) {
        Integer userId = getUserIdFromJwtToken(signedJWT);

        JWSHeader jwsHeader = signedJWT.getHeader();

        String keyId = jwsHeader.getKeyID();
        String algorithm = jwsHeader.getAlgorithm().getName();

        TokenODTO token = tokenService.getTokenByHash(hashString(signedJWT.serialize()));

        String jwkString = new String(Base64.from(token.getJwk()).decode());

        checkArgument(token.getUserId().equals(userId));
        checkNotNull(algorithm);
        checkNotNull(keyId);


        try {
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            checkArgument(claims.getAudience().contains(audienceUrl));
            checkArgument(claims.getIssuer().equals(issuerUrl));
            Date currentTime = Date.from(Instant.now(clock));
            checkArgument(claims.getIssueTime().before(currentTime));

            checkNotNull(claims.getSubject());
            checkNotNull(((Map<?, ?>) claims.getClaim(USER_CLAIMS)).get(USERNAME_CLAIM));
            JWK jwk = JWK.parse(jwkString);
            checkNotNull(jwk);
            checkArgument(jwk.getAlgorithm().getName().equals(algorithm));
            OctetKeyPair publicJWK = OctetKeyPair.parse(jwk.toJSONString()).toPublicJWK();
            checkNotNull(publicJWK);
            JWSVerifier jwsVerifier = new Ed25519Verifier(publicJWK);
            if(!signedJWT.verify(jwsVerifier))
                return INVALID;

            return claims.getExpirationTime().after(currentTime) && LocalDateTime.now().isBefore(token.getExpiresAt()) ? VALID : EXPIRED;

        } catch (ParseException | JOSEException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getEmailFromJwtToken(SignedJWT signedJWT) {
        try {
            return ((Map<?, ?>) signedJWT.getJWTClaimsSet().getClaim(USER_CLAIMS)).get(USERNAME_CLAIM).toString();
        } catch (ParseException ex) {
            throw new RequestException(BAD_REQUEST, TOKEN_INVALID, ex);
        }
    }

    private SignedJWT generateSignedJWT(UserODTO userODTO, OctetKeyPair jwk, Date now, Map<String, String> claims, Date expirationTime) throws JOSEException, ParseException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(jwk.getKeyID())
                .issuer(issuerUrl)
                .issueTime(now)
                .subject(userODTO.getId().toString())
                .claim(USER_CLAIMS, copyOf(claims))
                .expirationTime(expirationTime)
                .audience(audienceUrl)
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(EdDSA)
                .keyID(jwk.getKeyID())
                .build(),
                claimsSet);

        JWSSigner signer = new Ed25519Signer(jwk);
        signedJWT.sign(signer);
        String s = signedJWT.serialize();
        signedJWT = SignedJWT.parse(s);

        return signedJWT;
    }

    private Map<String, String> getClaims(UserODTO userODTO, TokenType tokenType) {
        return switch (tokenType) {
            case ANONYMOUS, RECOVERY, SIGNATURE -> new HashMap<>();
            case AUTHORIZATION, REFRESH, CONFIRM -> getUserFilledClaims(userODTO, tokenType);
        };
    }

    private Map<String, String> getUserFilledClaims(UserODTO userODTO, TokenType tokenType) {
        HashMap<String, String> userFilledClaims = new HashMap<>();
        userFilledClaims.put(USERNAME_CLAIM, userODTO.getUsername());
        userFilledClaims.put(ROLE_CLAIM, userODTO.getRole().name());
        userFilledClaims.put(TOKEN_TYPE_CLAIM, tokenType.name());
        return userFilledClaims;
    }

    private static OctetKeyPair generateOctetKeyPair() throws JOSEException {
        return new OctetKeyPairGenerator(Ed25519)
                .keyUse(SIGNATURE)
                .keyID(randomUUID().toString())
                .algorithm(EdDSA)
                .generate();
    }

    public Integer getUserIdFromJwtToken(SignedJWT signedJWT) {
        try {
            return valueOf(signedJWT.getJWTClaimsSet().getSubject());
        } catch (ParseException ex) {
            throw new RequestException(BAD_REQUEST, TOKEN_INVALID, ex);
        }
    }
}
