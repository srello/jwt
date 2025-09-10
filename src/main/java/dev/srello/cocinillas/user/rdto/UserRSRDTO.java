package dev.srello.cocinillas.user.rdto;

import dev.srello.cocinillas.user.enums.Role;

public record UserRSRDTO(
        Integer id,
        String email,
        String username,
        String name,
        String surname,
        Role role) {

}
