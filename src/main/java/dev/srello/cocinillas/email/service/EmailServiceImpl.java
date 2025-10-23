package dev.srello.cocinillas.email.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static dev.srello.cocinillas.auth.controller.AuthController.*;
import static dev.srello.cocinillas.core.codes.messages.Codes.Error.EMAIL_SEND_ERROR_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.EMAIL_SEND_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String CONFIRMATION_SUBJECT = "Confirma tu cuenta de Cocinillas APP";
    public static final String RECOVERY_SUBJECT = "Cambia tu contraseña de Cocinillas APP";
    MustacheFactory mustacheFactory = new DefaultMustacheFactory("templates");
    @Value("${email.app-direction}")
    private String fromEmail;
    @Value("${email.host}")
    private String emailHost;
    @Value("${email.port}")
    private Integer emailPort;
    @Value("${email.username}")
    private String emailUsername;
    @Value("${email.password}")
    private String emailPassword;
    @Value("${front.url}")
    private String frontUrl;

    public void sendConfirmationEmail(UserODTO userODTO, SignedJWT token) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", userODTO.getName());
        variables.put("link", frontUrl + AUTH_ROUTE + CONFIRM_ENDPOINT + "?token=" + token.getParsedString());
        String emailBody = getEmailBody("confirmation.mustache", variables);

        Email email = EmailBuilder.startingBlank()
                .from(fromEmail)
                .to(userODTO.getEmail())
                .withSubject(CONFIRMATION_SUBJECT)
                .withHTMLText(emailBody)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer(emailHost, emailPort, emailUsername, emailPassword)
                .buildMailer()) {
            mailer.sendMail(email);
        } catch (Exception e) {
            throw new RequestException(INTERNAL_SERVER_ERROR, EMAIL_SEND_ERROR, EMAIL_SEND_ERROR_CODE);
        }
    }

    public void sendRecoveryEmail(UserODTO userODTO, SignedJWT token) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", userODTO.getName());
        variables.put("link", frontUrl + AUTH_ROUTE + RESET_ENDPOINT + "?token=" + token.getParsedString());
        String emailBody = getEmailBody("recovery.mustache", variables);

        Email email = EmailBuilder.startingBlank()
                .from(fromEmail)
                .to(userODTO.getEmail())
                .withSubject(RECOVERY_SUBJECT)
                .withHTMLText(emailBody)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer(emailHost, emailPort, emailUsername, emailPassword)
                .buildMailer()) {
            mailer.sendMail(email);
        } catch (Exception e) {
            throw new RequestException(INTERNAL_SERVER_ERROR, EMAIL_SEND_ERROR, EMAIL_SEND_ERROR_CODE);
        }
    }

    private String getEmailBody(String templateName, Map<String, String> variables) {
        Mustache mustache = mustacheFactory.compile(templateName);
        Writer emailBodyWriter = new StringWriter();
        mustache.execute(emailBodyWriter, variables);
        return emailBodyWriter.toString();
    }
}
