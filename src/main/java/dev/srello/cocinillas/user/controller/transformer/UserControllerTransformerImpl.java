package dev.srello.cocinillas.user.controller.transformer;

import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.rdto.UserRSRDTO;
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
