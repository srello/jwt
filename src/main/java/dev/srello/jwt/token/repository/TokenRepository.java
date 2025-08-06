package dev.srello.jwt.token.repository;

import dev.srello.jwt.token.enums.TokenType;
import dev.srello.jwt.token.model.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository <Token, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE from Token t WHERE t.userId = :userId")
    void deleteAllTokensFromUser(Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE from Token t WHERE t.userId = :userId AND t.tokenType = :type")
    void deleteAllTokensFromUserAndType(Integer userId, TokenType type);

    Optional<Token> findByHash(String hash);
}
