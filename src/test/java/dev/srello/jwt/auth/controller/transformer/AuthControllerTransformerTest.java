package dev.srello.jwt.auth.controller.transformer;

import dev.srello.jwt.BaseTestClass;
import dev.srello.jwt.auth.dto.LoginIDTO;
import dev.srello.jwt.auth.rdto.LoginRQRDTO;
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
    void shouldSuccess_toIDTO(){
        LoginRQRDTO loginRQRDTO = generateData(LoginRQRDTO.class);

        LoginIDTO result = authControllerTransformer.toIDTO(loginRQRDTO);

        assertEquals(loginRQRDTO.password(), result.getPassword());
        assertEquals(loginRQRDTO.username(), result.getUsername());
        verify(authControllerTransformerMapper).toIDTO(loginRQRDTO);
    }
    @Test
    void shouldFail_toIDTO(){
        Executable executable = () -> authControllerTransformer.toIDTO(null);
        assertThrows(NullPointerException.class, executable);

        verify(authControllerTransformerMapper, never()).toIDTO(any());
    }
}
