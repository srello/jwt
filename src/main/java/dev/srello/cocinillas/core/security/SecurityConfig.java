package dev.srello.cocinillas.core.security;

import dev.srello.cocinillas.core.security.jwt.AuthEntryPointJwt;
import dev.srello.cocinillas.core.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static dev.srello.cocinillas.auth.controller.AuthController.*;
import static java.util.List.of;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PERMITTED_ENDPOINTS = {AUTH_ROUTE + LOGIN_ENDPOINT, AUTH_ROUTE + REGISTER_ENDPOINT, AUTH_ROUTE + CONFIRM_ENDPOINT, AUTH_ROUTE + RESEND_ENDPOINT};
    public static final List<String> PERMITTED_ENDPOINTS_LIST = of(PERMITTED_ENDPOINTS);
    private final AuthTokenFilter authTokenFilter;
    private final OriginFilter originFilter;
    private final PasswordEncoder passwordEncoder;
    private final AuthEntryPointJwt authEntryPointJwt;


    @Value("${security.cors}")
    private String corsOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headersConfig ->
                        headersConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PERMITTED_ENDPOINTS).permitAll()
                        .anyRequest().authenticated())
                .securityMatcher("/**")
                .addFilterBefore(originFilter, CorsFilter.class)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(config ->
                        config.authenticationEntryPoint(authEntryPointJwt));

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(of(corsOrigins.split(",")));

        log.info("Allowed CORS: {}", Arrays.toString(corsOrigins.split(",")));

        config.setAllowedMethods(of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(of("Authorization", "Accept", "Accept-Language", "content-type", "Requestor-Type", "X-Requested-With", "x-header-payload-token", "x-signature-token", "x-refresh-token"));
        config.setExposedHeaders(of("Allow", "X-Get-Header", "x-header-payload-token", "x-signature-token"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
