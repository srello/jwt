package dev.srello.jwt.auth.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.user.dto.UserODTO;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;

public interface AuthService {

    UserODTO login(LoginIDTO loginIDTO, HttpServletResponse response) throws ParseException, JOSEException;

    void refresh(UserODTO userODTO, HttpServletResponse response, SignedJWT refreshToken) throws ParseException, JOSEException;

    void logout(UserODTO userODTO, HttpServletResponse response);
}
