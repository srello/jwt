package dev.srello.jwt.auth.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.jwt.auth.controller.transformer.AuthControllerTransformer;
import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.auth.rdto.LoginRQRDTO;
import dev.srello.jwt.auth.service.AuthService;
import dev.srello.jwt.user.CurrentUser;
import dev.srello.jwt.user.controller.transformer.UserControllerTransformer;
import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.rdto.UserRSRDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static dev.srello.jwt.core.request.RequestConstants.REFRESH_REQUEST_ATTRIBUTE;


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

        return ResponseEntity.ok().body(userRSRDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CurrentUser UserODTO userODTO, HttpServletResponse response) {
        authService.logout(userODTO, response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(@CurrentUser UserODTO userODTO, HttpServletResponse response, @RequestAttribute(REFRESH_REQUEST_ATTRIBUTE)SignedJWT refreshToken) throws ParseException, JOSEException {
        authService.refresh(userODTO, response, refreshToken);

        return ResponseEntity.ok().build();
    }
}
