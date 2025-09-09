package dev.srello.cocinillas.core.security.jwt;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.cookie.service.CookieService;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.jwt.enums.JwtValidity;
import dev.srello.cocinillas.jwt.service.JwtService;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;

import static dev.srello.cocinillas.core.messages.Messages.Error.TOKEN_INVALID;
import static dev.srello.cocinillas.core.request.RequestConstants.*;
import static dev.srello.cocinillas.core.security.SecurityConfig.PERMITTED_ENDPOINTS_LIST;
import static dev.srello.cocinillas.jwt.enums.JwtValidity.EXPIRED;
import static dev.srello.cocinillas.token.enums.TokenType.*;
import static io.micrometer.common.util.StringUtils.isBlank;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String REFRESH_ENDPOINT = "/auth/refresh";
    private final CookieService cookieService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String endpoint = request.getAttribute(ENDPOINT_ATTRIBUTE).toString();
        if (PERMITTED_ENDPOINTS_LIST.contains(endpoint)) {
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> cookies = cookieService.getCookiesMap(request.getCookies());
        String authJwt = cookies.get(AUTHORIZATION.name());
        String signatureJwt = cookies.get(SIGNATURE.name());
        String refreshJwt = cookies.get(REFRESH.name());

        if (isBlank(authJwt) || isBlank(signatureJwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = format("Bearer %s.%s", authJwt, signatureJwt);

        SignedJWT signedJWT = jwtService.getDecodedJwt(jwt);

        try {
            JwtValidity jwtValidity = jwtService.verifyJwt(signedJWT);
            if (jwtValidity.equals(JwtValidity.INVALID) || (jwtValidity.equals(EXPIRED) && !REFRESH_ENDPOINT.equals(endpoint)))
                throw new RequestException(UNAUTHORIZED, TOKEN_INVALID);

        } catch (RequestException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
            return;
        }


        String userEmail = jwtService.getEmailFromJwtToken(signedJWT);

        UserODTO userODTO = userService.getByUsername(userEmail);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userODTO,
                null, userODTO.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.setAttribute(AUTHORIZATION_REQUEST_ATTRIBUTE, jwt);

        response.setHeader(AUTHORIZATION.name(), authJwt);

        if (REFRESH_ENDPOINT.equals(endpoint))
            request.setAttribute(REFRESH_REQUEST_ATTRIBUTE, jwtService.getDecodedJwt(refreshJwt));

        filterChain.doFilter(request, response);
    }
}
