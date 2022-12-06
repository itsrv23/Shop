package ru.got.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user@gmail.com")
                .password(passwordEncoder().encode("usr"))
                .authorities(Role.USER.getAuthorities())
                .build();
        UserDetails admin = User.builder()
                .username("admin@gmail.com")
                .password(passwordEncoder().encode("adm"))
                .authorities(Role.ADMIN.getAuthorities())
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(auth -> auth.mvcMatchers(AUTH_WHITELIST)
                        .permitAll()
//                        .mvcMatchers(HttpMethod.GET, "/ads/**", "/users/**")
//                        .hasAuthority(Permission.USERS_READ.getVALUE())
//                        .mvcMatchers(HttpMethod.DELETE, "/ads/**", "/users/**")
//                        .hasAuthority(Permission.USERS_WRITE.getVALUE())
                        .anyRequest()
                        .authenticated())
                .cors()
                .disable()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

