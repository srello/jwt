package dev.srello.cocinillas.auth.service.transformer;

import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.user.dto.UserIDTO;
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
}
