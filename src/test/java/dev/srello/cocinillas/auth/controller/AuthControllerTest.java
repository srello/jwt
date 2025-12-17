package dev.srello.cocinillas.auth.controller;

import com.nimbusds.jwt.SignedJWT;
import dev.srello.cocinillas.BaseTestClass;
import dev.srello.cocinillas.auth.controller.transformer.AuthControllerTransformer;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.service.AuthService;
import dev.srello.cocinillas.user.controller.transformer.UserControllerTransformer;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.rdto.UserRSRDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

class AuthControllerTest extends BaseTestClass {
    @InjectMocks
    AuthController authController;
    @Mock
    AuthService authService;
    @Mock
    AuthControllerTransformer authControllerTransformer;
    @Mock
    UserControllerTransformer userControllerTransformer;
    @Mock
    HttpServletResponse response;

    @Test
    void shouldSucceed_login() {
        LoginRQRDTO loginRQRDTO = generateData(LoginRQRDTO.class);
        LoginIDTO loginIDTO = generateData(LoginIDTO.class);
        UserODTO userODTO = generateData(UserODTO.class);
        UserRSRDTO userRSRDTO = generateData(UserRSRDTO.class);

        doReturn(loginIDTO).when(authControllerTransformer).toLoginIDTO(loginRQRDTO);
        doReturn(userODTO).when(authService).login(loginIDTO, response);
        doReturn(userRSRDTO).when(userControllerTransformer).toRSRDTO(userODTO);

        ResponseEntity<UserRSRDTO> result = authController.login(loginRQRDTO, response);

        assertEquals(userRSRDTO, result.getBody());
        verify(authControllerTransformer).toLoginIDTO(loginRQRDTO);
        verify(authService).login(loginIDTO, response);
        verify(userControllerTransformer).toRSRDTO(userODTO);
    }

    @Test
    void shouldSucceed_logout() {
        UserODTO userODTO = generateData(UserODTO.class);
        doNothing().when(authService).logout(userODTO, response);

        ResponseEntity<String> result = authController.logout(userODTO, response);


        assertEquals(OK, result.getStatusCode());
        verify(authService).logout(userODTO, response);
    }

    @Test
    void shouldSucceed_refresh() {
        UserODTO userODTO = generateData(UserODTO.class);
        SignedJWT signedJWT = generateData(SignedJWT.class);

        doNothing().when(authService).refresh(userODTO, response, signedJWT);

        ResponseEntity<Void> result = authController.refresh(userODTO, response, signedJWT);


        assertEquals(OK, result.getStatusCode());
        verify(authService).refresh(userODTO, response, signedJWT);
    }
}
