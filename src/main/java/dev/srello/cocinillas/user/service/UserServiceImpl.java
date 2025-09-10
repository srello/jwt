package dev.srello.cocinillas.user.service;

import dev.srello.cocinillas.core.exception.RequestException;
import dev.srello.cocinillas.user.dto.UserIDTO;
import dev.srello.cocinillas.user.dto.UserODTO;
import dev.srello.cocinillas.user.dto.UserUpdateIDTO;
import dev.srello.cocinillas.user.model.User;
import dev.srello.cocinillas.user.repository.UserRepository;
import dev.srello.cocinillas.user.service.transformer.UserServiceTransformer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static dev.srello.cocinillas.core.messages.Messages.Error.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserServiceTransformer userServiceTransformer;

    @Override
    public UserODTO getByUsername(String username) {
        User user = findByUsername(username);
        return userServiceTransformer.toODTO(user);
    }

    @Override
    public UserODTO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RequestException(NOT_FOUND, USER_NOT_FOUND));
        return userServiceTransformer.toODTO(user);
    }

    @Override
    public UserODTO createUser(UserIDTO userIDTO) {
        User user = userServiceTransformer.toUser(userIDTO);
        User userFromDB = userRepository.saveAndFlush(user);
        return userServiceTransformer.toODTO(userFromDB);
    }

    @Override
    public UserODTO updateUser(UserUpdateIDTO userUpdateIDTO) {
        User user = userServiceTransformer.toUser(userUpdateIDTO);
        User userFromDB = userRepository.saveAndFlush(user);
        return userServiceTransformer.toODTO(userFromDB);
    }

    @Override
    public @NotNull UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RequestException(NOT_FOUND, USER_NOT_FOUND));
    }
}
