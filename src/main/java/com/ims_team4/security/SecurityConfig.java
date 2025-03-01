package com.ims_team4.security;

import com.ims_team4.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * <p>Configures setting for Login, Logout and “Remember Me” authentication.</p>
     * <ul>
     *     <li>
     *         <b>Login:</b>
     *         <p>
     *             The context will redirect to url /login in order to open the login page.
     *             After that, if login is successful, the context will redirect to default url. Otherwise, redirect to /login with attribute "error
     *         </p>
     *     </li>
     *     <li>
     *         <b>Logout:</b>
     *         <p>
     *             When clicking the "Logout" button, context will delete all cookies and session.
     *             After that, redirecting to url "/login"
     *         </p>
     *     </li>
     *     <li>
     *         <b>Remember me:</b>
     *         <p>
     *             When clicking the "Remember Me" button, context will save all login session information into database.
     *             After that, save existing time of session (7 days) and user information from UserDetailsService
     *         </p>
     *     </li>
     * </ul>
     * @param http     The HttpSecurity configuration.
     * @param service  The UserDetailsService used for authentication.
     * @param source   The DataSource used for persistent token storage.
     * @return A configured SecurityFilterChain bean.
     * @throws Exception if an error occurs during security configuration.
     * @author HoanTX
     */
    @Bean
    public SecurityFilterChain loginLogoutFilterChain(HttpSecurity http, UserDetailsService service, DataSource source) throws Exception {
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
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSessionID", "remember-me"))
                .rememberMe(me -> me
                        .tokenRepository(persistentTokenRepository(source))
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .userDetailsService(service)
                        .alwaysRemember(true))
                .httpBasic(Customizer.withDefaults()).build();
    }

     /**
     * Provides a password encoder using BCrypt.
     * 
     * @return A BCryptPasswordEncoder instance.
     * @author HoanTX
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Loads user information based on the email entered during login.
     * The user details are managed in the Spring Security context.
     *
     * @param service The user service used to fetch user details.
     * @return A UserDetailsService implementation.
     * @author HoanTX
     */
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

    /**
     * Stores login token in the database instead of cookie
     * 
     * @param dataSource The data source used for token persistence.
     * @return A PersistentTokenRepository implementation using JDBC.
     * @author HoanTX
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

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
}
