package dev.srello.cocinillas.email.service;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.user.dto.UserODTO;

import java.util.Map;

public interface EmailService {
    void sendConfirmationEmail(UserODTO userODTO, SignedJWT token);

    String getEmailBody(String templateName, Map<String, String> variables);
}
