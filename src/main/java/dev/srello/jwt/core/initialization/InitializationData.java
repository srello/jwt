package dev.srello.jwt.core.initialization;


import dev.srello.jwt.user.model.User;
import dev.srello.jwt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static dev.srello.jwt.user.enums.Role.ADMIN;
import static dev.srello.jwt.user.enums.Role.USER;
import static java.util.List.of;

@Component
@RequiredArgsConstructor
@Profile("!pro")
public class InitializationData {
    @Value("${LOAD_INITIAL_DATA:false}")
    private boolean loadInitialData;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Bean
    @Profile("dev")
    public InitializingBean intialize(){
        return this::createDbData;
    }

    private void createDbData() {
        if(!loadInitialData) return;

        User admin = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(ADMIN)
                .username("admin@admin.com")
                .build();
        User user = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(USER)
                .username("user@user.com")
                .build();

        userRepository.saveAllAndFlush(of(admin, user));
    }
}
