package dev.srello.cocinillas.user.service;

import dev.srello.cocinillas.core.exception.custom.RequestException;
import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.model.User;
import dev.srello.cocinillas.user.repository.UserRepository;
import dev.srello.cocinillas.user.service.transformer.UserServiceTransformer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static dev.srello.cocinillas.core.codes.messages.Codes.Error.USER_NOT_FOUND_CODE;
import static dev.srello.cocinillas.core.messages.Messages.Error.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserServiceTransformer transformer;

    @Override
    public UserODTO getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RequestException(NOT_FOUND, USER_NOT_FOUND, USER_NOT_FOUND_CODE));
        return transformer.toODTO(user);
    }

    @Override
    public UserODTO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RequestException(NOT_FOUND, USER_NOT_FOUND, USER_NOT_FOUND_CODE));
        return transformer.toODTO(user);
    }

    @Override
    public UserODTO createUser(UserIDTO userIDTO) {
        User user = transformer.toUser(userIDTO);
        User userFromDB = userRepository.saveAndFlush(user);
        return transformer.toODTO(userFromDB);
    }

    @Override
    public UserODTO updateUser(UserODTO userODTO) {
        User user = transformer.toUser(userODTO);
        User userFromDB = userRepository.saveAndFlush(user);
        return transformer.toODTO(userFromDB);
    }

    @Override
    public @NotNull UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }
}
