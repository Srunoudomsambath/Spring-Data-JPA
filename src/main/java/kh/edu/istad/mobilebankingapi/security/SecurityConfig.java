package kh.edu.istad.mobilebankingapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception{

        // TODO
        // 1. make all endpoints secure
        http.authorizeHttpRequests(endpoints -> endpoints
                .anyRequest()
                .authenticated()
        );
        // Disable form login of web
        http.formLogin(form -> form.disable());

        // Set security mechanism = HTTP Basic Authentication => JWT, OAUTH2
        http.httpBasic(Customizer.withDefaults());

        // CSRF common protection => CSS Tokenn
        http.csrf(token -> token.disable());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 2. make stateless api

        return http.build();


    }
}
