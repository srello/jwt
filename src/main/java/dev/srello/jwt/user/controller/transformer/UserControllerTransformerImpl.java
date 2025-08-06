package dev.srello.jwt.user.controller.transformer;

import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.rdto.UserRSRDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserControllerTransformerImpl implements UserControllerTransformer {
    private final UserControllerMapper userControllerMapper;
    @Override
    public UserRSRDTO toRSRDTO(UserODTO userODTO) {
        return userControllerMapper.toRSRDTO(userODTO);
    }
}
