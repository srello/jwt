package dev.srello.jwt.user.service;

import dev.srello.jwt.core.exception.RequestException;
import dev.srello.jwt.user.dto.UserODTO;
import dev.srello.jwt.user.model.User;
import dev.srello.jwt.user.repository.UserRepository;
import dev.srello.jwt.user.service.transformer.UserServiceTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static dev.srello.jwt.core.messages.Messages.Error.USER_NOT_FOUND;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RequestException(NOT_FOUND, USER_NOT_FOUND));
    }
}
