package dev.srello.jwt.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.stream.Stream.of;
import static org.springframework.security.crypto.argon2.Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8;

@Component
public class ArgonPassword {

    @Value("${spring.security.crypto.password.argon2.salt-length:16}")
    private Integer argonSaltLength;
    @Value("${spring.security.crypto.password.argon2.hash-length:32}")
    private Integer argonHashLength;
    @Value("${spring.security.crypto.password.argon2.parallelism:2}")
    private Integer argonThreads;
    @Value("${spring.security.crypto.password.argon2.memory:16384}")
    private Integer argonMemory;
    @Value("${spring.security.crypto.password.argon2.iterations:5}")
    private Integer argonIterations;

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        if(of(argonSaltLength, argonHashLength, argonThreads, argonMemory, argonIterations).noneMatch(Objects::isNull))
            return defaultsForSpringSecurity_v5_8();

        return new Argon2PasswordEncoder(argonSaltLength, argonHashLength, argonThreads, argonMemory, argonIterations);
    }
}
