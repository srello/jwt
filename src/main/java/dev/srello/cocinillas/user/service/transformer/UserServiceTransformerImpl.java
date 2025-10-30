package dev.srello.cocinillas.user.service.transformer;

import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceTransformerImpl implements UserServiceTransformer {

    private final UserServiceMapper userServiceMapper;

    @Override
    public UserODTO toODTO(@NonNull User user) {
        return userServiceMapper.toODTO(user);
    }

    @Override
    public User toUser(@NonNull UserIDTO userIDTO) {
        return userServiceMapper.toUser(userIDTO);
    }

    @Override
    public User toUser(@NonNull UserODTO userODTO) {
        return userServiceMapper.toUser(userODTO);
    }
}
