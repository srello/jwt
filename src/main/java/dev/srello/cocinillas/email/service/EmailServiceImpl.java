package dev.srello.cocinillas.email.service;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.core.messages.Messages;
import dev.srello.cocinillas.user.dto.UserODTO;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static dev.srello.cocinillas.auth.controller.AuthController.AUTH_ROUTE;
import static dev.srello.cocinillas.auth.controller.AuthController.CONFIRM_ENDPOINT;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String CONFIRMATION_SUBJECT = "Confirma tu cuenta de Cocinillas APP";
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
            throw new RequestException(HttpStatus.INTERNAL_SERVER_ERROR, Messages.Error.EMAIL_SEND_ERROR);
        }
    }

    public String getEmailBody(String templateName, Map<String, String> variables) {
        Mustache mustache = mustacheFactory.compile(templateName);
        Writer emailBodyWriter = new StringWriter();
        mustache.execute(emailBodyWriter, variables);
        return emailBodyWriter.toString();
    }
}
