package dev.srello.cocinillas.user.service.transformer;

import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.dto.UserUpdateIDTO;
import dev.srello.cocinillas.user.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserServiceMapper {
    UserODTO toODTO(User user);

    User toUser(UserIDTO userIDTO);

    User toUser(UserUpdateIDTO userUpdateIDTO);
}
