package dev.srello.cocinillas.auth.service.transformer;

import dev.srello.cocinillas.auth.dto.RegisterIDTO;
import dev.srello.cocinillas.user.dto.UserIDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AuthServiceMapper {
    @Mapping(target = "role", constant = "NULL")
    UserIDTO toUserIDTO(RegisterIDTO registerIDTO);
}
