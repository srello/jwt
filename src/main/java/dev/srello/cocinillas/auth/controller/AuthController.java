package dev.srello.cocinillas.auth.controller;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.auth.controller.transformer.AuthControllerTransformer;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.rdto.RegisterRQRDTO;
import dev.srello.cocinillas.auth.rdto.ResendRQRDTO;
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

import static dev.srello.cocinillas.auth.controller.AuthController.AUTH_ROUTE;
import static dev.srello.cocinillas.core.request.RequestConstants.REFRESH_REQUEST_ATTRIBUTE;
import static org.springframework.http.ResponseEntity.ok;


@Controller
@RequiredArgsConstructor
@RequestMapping(AUTH_ROUTE)
public class AuthController {
    public static final String CONFIRM_ENDPOINT = "/confirm";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REGISTER_ENDPOINT = "/register";
    public static final String AUTH_ROUTE = "/auth";
    public static final String RESEND_ENDPOINT = "/resend";

    private final AuthService authService;
    private final AuthControllerTransformer authControllerTransformer;
    private final UserControllerTransformer userControllerTransformer;

    @PostMapping(REGISTER_ENDPOINT)
    public ResponseEntity<Void> register(@RequestBody RegisterRQRDTO registerRQRDTO) {
        RegisterIDTO registerIDTO = authControllerTransformer.toRegisterIDTO(registerRQRDTO);
        authService.register(registerIDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(LOGIN_ENDPOINT)
    public ResponseEntity<UserRSRDTO> login(@RequestBody LoginRQRDTO loginRQRDTO, HttpServletResponse response) {
        LoginIDTO loginIDTO = authControllerTransformer.toLoginIDTO(loginRQRDTO);
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
    public ResponseEntity<Void> refresh(@CurrentUser UserODTO userODTO, HttpServletResponse response, @RequestAttribute(REFRESH_REQUEST_ATTRIBUTE) SignedJWT refreshToken) {
        authService.refresh(userODTO, response, refreshToken);

        return ok().build();
    }

    @GetMapping(CONFIRM_ENDPOINT)
    public ResponseEntity<UserRSRDTO> confirm(@RequestParam("token") String token, HttpServletResponse response) {
        UserODTO userODTO = authService.confirm(token, response);
        UserRSRDTO userRSRDTO = userControllerTransformer.toRSRDTO(userODTO);
        return ok().body(userRSRDTO);
    }

    @PostMapping(RESEND_ENDPOINT)
    public ResponseEntity<Void> resend(@RequestBody ResendRQRDTO resendRQRDTO) {
        authService.resendEmail(resendRQRDTO.email());
        return ok().build();
    }
}
