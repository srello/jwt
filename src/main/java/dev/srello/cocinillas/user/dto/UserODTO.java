package dev.srello.cocinillas.user.dto;

import dev.srello.cocinillas.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.List.of;

@AllArgsConstructor
@Getter
@Setter
public class UserODTO implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private Role role;

    @Override
    public @NotNull Collection<? extends GrantedAuthority> getAuthorities() {
        return of(new SimpleGrantedAuthority(role.name()));
    }
}
