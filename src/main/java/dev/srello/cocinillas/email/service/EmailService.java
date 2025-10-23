package dev.srello.cocinillas.email.service;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.user.dto.UserODTO;

public interface EmailService {
    void sendConfirmationEmail(UserODTO userODTO, SignedJWT token);

    void sendRecoveryEmail(UserODTO userODTO, SignedJWT token);
}
