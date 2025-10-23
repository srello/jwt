package dev.srello.cocinillas.auth.service;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.dto.ResetPasswordIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    UserODTO login(LoginIDTO loginIDTO, HttpServletResponse response);

    void refresh(UserODTO userODTO, HttpServletResponse response, SignedJWT refreshToken);

    void logout(UserODTO userODTO, HttpServletResponse response);

    void register(RegisterIDTO registerIDTO);

    UserODTO confirm(String token, HttpServletResponse response);

    void resendEmail(String email);

    void forgotPassword(String email);

    UserODTO resetPassword(ResetPasswordIDTO loginIDTO, HttpServletResponse response);
}
