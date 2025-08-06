package dev.srello.jwt.token.dto;

import dev.srello.jwt.token.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TokenODTO {

    private UUID id;
    private LocalDateTime expiresAt;
    private LocalDateTime issuedAt;
    private String jwtID;
    private String jwk;
    private TokenType tokenType;
    private Integer userId;
    private String hash;
}
