package dev.srello.jwt.user.rdto;

import dev.srello.jwt.user.enums.Role;

public record UserRSRDTO(
        Integer id,
        String username,
        Role role) {

}
