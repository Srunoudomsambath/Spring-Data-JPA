package kh.edu.istad.mobilebankingapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeyCloakConfigSecurity {

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
    public DaoAuthenticationProvider daoAuthProvider(){
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
                .requestMatchers("/media/**").permitAll()
                .anyRequest()
                .authenticated()
        );
        // Disable form login of web
        http.formLogin(form -> form.disable());



        // TODO Set security mechanism = OAUTH2
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(Customizer.withDefaults()));



        // Set security mechanism = HTTP Basic Authentication => JWT, OAUTH2
//        http.httpBasic(Customizer.withDefaults());

        // CSRF common protection => CSS Token
        // No use form login spring
        http.csrf(token -> token.disable());
        // make new login by our self
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 2. make stateless api

        return http.build();


    }

    @Bean
    // NOTE if OAUTH2 watching array of string else AWS array of ...
    // ROLE_ prefix
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        Converter<Jwt,Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String , Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtConverter;
    }


}
