package dev.srello.cocinillas.token.model;

import dev.srello.cocinillas.token.enums.TokenType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(updatable = false, nullable = false)
    private LocalDateTime expiresAt;

    @NotNull
    @Column(updatable = false, nullable = false)
    private LocalDateTime issuedAt;

    @NotBlank
    @Column(updatable = false, nullable = false)
    private String jwtID;

    @NotBlank
    @Column(columnDefinition = "text", updatable = false, nullable = false)
    private String jwk;

    @NotNull
    @Column(updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @NotNull
    @Column(updatable = false, nullable = false)
    private Long userId;

    private String hash;

}
