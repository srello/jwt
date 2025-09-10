package dev.srello.cocinillas.auth.service.transformer;

import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.dto.UserUpdateIDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceTransformerImpl implements AuthServiceTransformer {
    private final AuthServiceMapper authServiceMapper;

    @Override
    public UserIDTO toUserIDTO(@NonNull RegisterIDTO registerIDTO) {

        return authServiceMapper.toUserIDTO(registerIDTO);
    }

    @Override
    public UserUpdateIDTO toUserUpdateIDTO(@NonNull UserODTO userODTO) {
        return authServiceMapper.toUserUpdateIDTO(userODTO);
    }
}
