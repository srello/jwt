package dev.srello.cocinillas.auth.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.auth.controller.transformer.AuthControllerTransformer;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.service.AuthService;
import dev.srello.cocinillas.user.CurrentUser;
import dev.srello.cocinillas.user.controller.transformer.UserControllerTransformer;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.rdto.UserRSRDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static dev.srello.cocinillas.core.request.RequestConstants.REFRESH_REQUEST_ATTRIBUTE;
import static org.springframework.http.ResponseEntity.ok;


@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthControllerTransformer authControllerTransformer;
    private final UserControllerTransformer userControllerTransformer;

    @PostMapping("/login")
    public ResponseEntity<UserRSRDTO> login(@RequestBody LoginRQRDTO loginRQRDTO, HttpServletResponse response) throws ParseException, JOSEException {
        LoginIDTO loginIDTO = authControllerTransformer.toIDTO(loginRQRDTO);
        UserODTO userODTO = authService.login(loginIDTO, response);
        UserRSRDTO userRSRDTO = userControllerTransformer.toRSRDTO(userODTO);

        return ok().body(userRSRDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CurrentUser UserODTO userODTO, HttpServletResponse response) {
        authService.logout(userODTO, response);

        return ok().build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(@CurrentUser UserODTO userODTO, HttpServletResponse response, @RequestAttribute(REFRESH_REQUEST_ATTRIBUTE)SignedJWT refreshToken) throws ParseException, JOSEException {
        authService.refresh(userODTO, response, refreshToken);

        return ok().build();
    }
}
