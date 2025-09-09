package dev.srello.cocinillas.user.service.transformer;

import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserServiceMapper {
    UserODTO toODTO(User user);
}
