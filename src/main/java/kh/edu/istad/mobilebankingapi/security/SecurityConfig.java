package kh.edu.istad.mobilebankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        private final UserDetailsService userDetailsService;
        private final PasswordEncoder passwordEncoder;
        private final String ROLE_ADMIN = "ADMIN";
        private final String ROLE_CUSTOMER = "CUSTOMER";
        private final String ROLE_STAFF = "STAFF";

        // we need to encrypt password not like that {noop} will hash the password
//        @Bean
//        public InMemoryUserDetailsManager configureUsers(){
//            InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//            UserDetails admin = User.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("qwer"))
//                    .roles(ROLE_ADMIN)
//                    .build();
//            manager.createUser(admin);
//
//            UserDetails staff = User.builder()
//                    .username("staff")
//                    .password(passwordEncoder.encode("qwer"))
//                    .roles(ROLE_STAFF)
//                    .build();
//            manager.createUser(staff);
//
//            UserDetails customer = User.builder()
//                    .username("customer")
//                   .password("{noop}qwer")
//                    .password(passwordEncoder.encode("qwer"))
//                    .roles(ROLE_CUSTOMER)
//                    .build();
//            manager.createUser(customer);
//
//            return manager;
//        }

        // to checkup authentication in databases
        @Bean
        public DaoAuthenticationProvider  daoAuthProvider(){
            DaoAuthenticationProvider daoAuthProvider =
                    new DaoAuthenticationProvider(userDetailsService);
            daoAuthProvider.setPasswordEncoder(passwordEncoder);
            return daoAuthProvider;
        }


    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception{
        // TODO
        // 1. make all endpoints secure
        http.authorizeHttpRequests(endpoints -> endpoints
                //  authorization makes customer and staff can access this endpoints
                .requestMatchers(HttpMethod.POST).hasAnyRole(ROLE_ADMIN,ROLE_STAFF)
                .requestMatchers(HttpMethod.PUT).hasAnyRole(ROLE_ADMIN,ROLE_STAFF)
                .requestMatchers(HttpMethod.DELETE).hasAnyRole(ROLE_ADMIN)
                .requestMatchers(HttpMethod.GET).hasAnyRole(ROLE_STAFF,ROLE_CUSTOMER,ROLE_ADMIN)
                .requestMatchers("api/customer/accounts/**").hasAnyRole(ROLE_CUSTOMER,ROLE_STAFF,ROLE_ADMIN)
                .anyRequest()
                .authenticated()
        );
        // Disable form login of web
        http.formLogin(form -> form.disable());

        // Set security mechanism = HTTP Basic Authentication => JWT, OAUTH2
        http.httpBasic(Customizer.withDefaults());

        // CSRF common protection => CSS Tokenn
        // No use form login spring
        http.csrf(token -> token.disable());
        // make new login by our self
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 2. make stateless api

        return http.build();


    }
}
