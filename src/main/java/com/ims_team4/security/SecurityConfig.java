package com.ims_team4.security;

import com.ims_team4.dto.UserDTO;
import com.ims_team4.service.UserService;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // @Bean
    // public SecurityFilterChain generalConfiguration(HttpSecurity http) throws
    // Exception {
    // return http
    // .csrf(AbstractHttpConfigurer::disable)
    // .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
    // .requestMatchers("/interviewer-features/**").hasAnyRole("INTERVIEW")
    // .requestMatchers("/manager-features/**").hasAnyRole("MANAGER")
    // .requestMatchers("/recruiter-features/**").hasAnyRole("RECRUITER")
    // .requestMatchers("/admin-features/**").hasAnyRole("ADMIN")
    // .anyRequest().authenticated()
    // ).httpBasic(Customizer.withDefaults()).build();
    // }
    //

    // HoanTX
    @Bean
    public SecurityFilterChain loginLogoutFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/login", "/logout")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSessionID"))
                .httpBasic(Customizer.withDefaults()).build();
    }

    // HoanTX
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HoanTX
    @Bean
    public UserDetailsService userDetailsService(UserService service) {
        return email -> {
            // Optional<UserDTO> userDTOs = service.getUserByEmail(email);
            // if (userDTOs.isEmpty()) {
            // throw new UsernameNotFoundException("User not found");
            // }
            // UserDTO userDTO1 = userDTOs.get();
            // return new User(userDTO1.getEmail(), userDTO1.getPassword(), new
            // ArrayList<>());
            BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
            return new User("user1", encode.encode("123"), new ArrayList<>());
        };
    }

}
