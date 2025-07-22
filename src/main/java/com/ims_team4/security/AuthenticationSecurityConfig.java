package com.ims_team4.security;

import com.ims_team4.config.Constants;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
// HoanTX
public class AuthenticationSecurityConfig implements Constants.Role {
    /**
     * <p>
     * Configures setting for Login, Logout and “Remember Me” authentication.
     * </p>
     * <ul>
     * <li>
     * <b>Login:</b>
     * <p>
     * The context will redirect to url /login in order to open the login page.
     * After that, if login is successful, the context will redirect to default url.
     * Otherwise, redirect to /login with attribute "error
     * </p>
     * </li>
     * <li>
     * <b>Logout:</b>
     * <p>
     * When clicking the "Logout" button, context will delete all cookies and
     * session.
     * After that, redirecting to url "/login"
     * </p>
     * </li>
     * <li>
     * <b>Remember me:</b>
     * <p>
     * When clicking the "Remember Me" button, context will save all login session
     * information into database.
     * After that, save existing time of session (7 days) and user information from
     * UserDetailsService
     * </p>
     * </li>
     * </ul>
     *
     * @param http    The HttpSecurity configuration.
     * @param service The UserDetailsService used for authentication.
     * @param source  The DataSource used for persistent token storage.
     * @return A configured SecurityFilterChain bean.
     * @throws Exception if an error occurs during security configuration.
     */
    @Bean
    public SecurityFilterChain commonFilterChain(HttpSecurity http, UserDetailsService service,
                                                 DataSource source) throws Exception {
        return http
                .securityMatcher("/login/**", "/logout", "/dashboard", "/forgot", "/setting", "/access_denied")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/login/**",
                                "/setting",
                                "/access_denied"
                        ).permitAll()

                        .requestMatchers(
                                "/dashboard",
                                "/forgot"
                        ).hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authorize) -> {
                            log.info("User has logged in: {}", authorize.getName());
                            response.sendRedirect("/dashboard");
                        })
                        .failureHandler((request, response, authorize) -> {
                            log.info("{} failed", request.getRequestURI());
                            log.info(authorize.getMessage());
                        })
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if (authentication != null) {
                                log.info("User has logged out: {}", authentication.getName());
                            }
                            response.sendRedirect("/login");
                        })
                        .deleteCookies("JSESSIONID", "remember-me"))
                .rememberMe(me -> me
                        .useSecureCookie(true)
                        .tokenRepository(persistentTokenRepository(source))
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .userDetailsService(service)
                        .key("ahadfcxvjweiosnaogp0913414#")
                        .alwaysRemember(false))
//                 When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.error("Error happens when exeptionHandling is working: {}", accessDeniedException.getMessage());
                            log.info("request uri: {}", request.getRequestURI());
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // 1 user can access with only 1 device, if he/she try to use with 2 or more, the previous session will immediately be canceled.
                        .expiredUrl("/login")
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Loads user information based on the email entered during login. The user details are managed in the Spring
     * Security context.
     *
     * @param uSrv The user service used to fetch user details.
     * @return A UserDetailsService implementation.
     * @author HoanTX
     */
    @Bean
    public UserDetailsService userDetailsService(UserService uSrv, EmployeeService empSrv) throws UsernameNotFoundException {
        return email -> {
            EmployeeDTO emp;
            try {
                UserDTO userDTO = uSrv.getUserByEmail(email);
                emp = getEmployeeDTOByID(userDTO.getId(), empSrv);
                if (!emp.isStatus()) {
                    throw new Exception("This account can not access");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
            GrantedAuthority authority = new SimpleGrantedAuthority(emp.getRole().name());
            return new User(emp.getEmail(), emp.getPassword(), List.of(authority));
        };
    }

    @NotNull
    private EmployeeDTO getEmployeeDTOByID(Long id, @NotNull EmployeeService empSrv) throws Exception {
        EmployeeDTO emp = empSrv.getActiveEmployeesByID(id);
        log.info(emp.toString());
        return emp;
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

    /**
     * Provides a password encoder using BCrypt.
     *
     * @return A BCryptPasswordEncoder instance.
     * @author HoanTX
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
