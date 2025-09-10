package dev.srello.cocinillas.core.initialization;


import dev.srello.cocinillas.user.model.User;
import dev.srello.cocinillas.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static dev.srello.cocinillas.user.enums.Role.ADMIN;
import static dev.srello.cocinillas.user.enums.Role.USER;
import static java.util.List.of;

@Component
@RequiredArgsConstructor
@Profile("!pro")
public class InitializationData {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${LOAD_INITIAL_DATA:false}")
    private boolean loadInitialData;

    @Bean
    @Profile("DEV")
    public InitializingBean intialize() {
        return this::createDbData;
    }

    private void createDbData() {
        if (!loadInitialData) return;

        User admin = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(ADMIN)
                .email("admin@admin.com")
                .username("admin")
                .name("Aname")
                .surname("Asurname")
                .build();
        User user = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(USER)
                .email("user@user.com")
                .username("user")
                .name("Uname")
                .surname("Usurname")
                .build();

        userRepository.saveAllAndFlush(of(admin, user));
    }
}
