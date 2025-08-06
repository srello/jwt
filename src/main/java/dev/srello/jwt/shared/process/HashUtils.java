package dev.srello.jwt.shared.process;

import dev.srello.jwt.core.exception.RequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static dev.srello.jwt.core.messages.Messages.Error.HASH_ALGORITHM_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtils {
    public static String hashString(String input) {
        try {
            return Hex.toHexString(MessageDigest.getInstance("SHA3-256").digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new RequestException(INTERNAL_SERVER_ERROR, HASH_ALGORITHM_ERROR);
        }
    }

}
