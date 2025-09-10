package dev.srello.cocinillas.auth.controller.transformer;

import dev.srello.cocinillas.BaseTestClass;
import dev.srello.cocinillas.auth.dto.LoginIDTO;
import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.auth.rdto.LoginRQRDTO;
import dev.srello.cocinillas.auth.rdto.RegisterRQRDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SuppressWarnings("DataFlowIssue")
class AuthControllerTransformerTest extends BaseTestClass {

    @InjectMocks
    AuthControllerTransformerImpl authControllerTransformer;

    @Spy
    AuthControllerTransformerMapper authControllerTransformerMapper = new AuthControllerTransformerMapperImpl();

    @Test
    void shouldSuccess_toLoginIDTO() {
        LoginRQRDTO loginRQRDTO = generateData(LoginRQRDTO.class);

        LoginIDTO result = authControllerTransformer.toLoginIDTO(loginRQRDTO);

        assertEquals(loginRQRDTO.password(), result.getPassword());
        assertEquals(loginRQRDTO.username(), result.getUsername());
        verify(authControllerTransformerMapper).toLoginIDTO(loginRQRDTO);
    }

    @Test
    void shouldFail_toLoginIDTO() {
        Executable executable = () -> authControllerTransformer.toLoginIDTO(null);
        assertThrows(NullPointerException.class, executable);

        verify(authControllerTransformerMapper, never()).toLoginIDTO(any());
    }

    @Test
    void shouldSuccess_toRegisterIDTO() {
        RegisterRQRDTO registerRQRDTO = generateData(RegisterRQRDTO.class);

        RegisterIDTO result = authControllerTransformer.toRegisterIDTO(registerRQRDTO);

        assertEquals(registerRQRDTO.password(), result.getPassword());
        assertEquals(registerRQRDTO.username(), result.getUsername());
        verify(authControllerTransformerMapper).toRegisterIDTO(registerRQRDTO);
    }

    @Test
    void shouldFail_toRegisterIDTO() {
        Executable executable = () -> authControllerTransformer.toRegisterIDTO(null);
        assertThrows(NullPointerException.class, executable);

        verify(authControllerTransformerMapper, never()).toRegisterIDTO(any());
    }
}
