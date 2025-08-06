package dev.srello.jwt.user.service.transformer;

import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserServiceMapper {
    UserODTO toODTO(User user);
}
