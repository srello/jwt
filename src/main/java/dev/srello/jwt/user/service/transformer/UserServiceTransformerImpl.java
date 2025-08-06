package dev.srello.jwt.user.service.transformer;

import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceTransformerImpl implements UserServiceTransformer {

    private final UserServiceMapper userServiceMapper;
    @Override
    public UserODTO toODTO(User user) {
        return userServiceMapper.toODTO(user);
    }
}
