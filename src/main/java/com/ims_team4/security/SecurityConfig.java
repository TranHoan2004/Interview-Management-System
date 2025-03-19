package com.ims_team4.security;

import com.ims_team4.config.Constants;
import com.ims_team4.dto.EmployeeDTO;
import com.ims_team4.dto.UserDTO;
import com.ims_team4.service.EmployeeService;
import com.ims_team4.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
// HoanTX
public class SecurityConfig implements Constants.Role {
    private final Logger log = Logger.getLogger(SecurityConfig.class.getName());

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
    public SecurityFilterChain commonFilterChain(HttpSecurity http,
                                                 UserDetailsService service,
                                                 DataSource source) throws Exception {
        return http
                .securityMatcher("/login", "/logout", "/dashboard", "/forgot")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/dashboard", "/forgot").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authorize) -> {
                            log.info("User has logged in: " + authorize.getName());
                            response.sendRedirect("/dashboard");
                        })
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if (authentication != null) {
                                log.info("User has logged out: " + authentication.getName());
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
                        .accessDeniedHandler((request, response, accessDeniedException) -> log.warning("Error happens when exeptionHandling is working: " + accessDeniedException.getMessage()))
                )
                .sessionManagement(session -> session
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
            EmployeeDTO emp = new EmployeeDTO();
            try {
                List<UserDTO> userDTOs = uSrv.getUserByEmail(email);
                if (userDTOs.isEmpty()) {
                    throw new UsernameNotFoundException("Account not found");
                }
                UserDTO userDTO = userDTOs.getFirst();
                emp = empSrv.getEmployeeById(Math.toIntExact(userDTO.getId()));
                if (emp == null) {
                    throw new Exception("Do not find because list is empty");
                }
                log.log(Level.INFO, emp.toString());
            } catch (Exception e) {
                Logger.getLogger(SecurityConfig.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            GrantedAuthority authority = new SimpleGrantedAuthority(emp.getRole().name());
            return new User(emp.getEmail(), emp.getPassword(), List.of(authority));
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

    // <editor-fold> desc="Authorize the URL"
    @Bean
    public SecurityFilterChain userFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/user/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/user/**").hasRole(ROLE_ADMINISTRATOR) // UC32, UC33, UC34, UC35, UC36
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/error")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain jobFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/jobs/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Full 4 actors can access this URL.
                        .requestMatchers(
                                "/jobs/list", // UC11 view job list
                                "/jobs/details" // UC13 view job details
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)

                        // Except of Interviewer, others can access this URL.
                        .requestMatchers(
                                "admin/listAllJob",
                                "/manager/list",
                                "/jobs/create", // UC12 create new job
                                "/jobs/edit", // UC14 edit job
                                "/jobs/delete" // UC15 delete job
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/error")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    public SecurityFilterChain interviewFunctionalFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/interview/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        // Full 4 actors can access this URL.
//                        .requestMatchers(
//                                "/interview/list", // UC16 view interview schedule list
//                                "/interview/interviewDetail" // UC18 view interview schedule details
//                        )
//                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // 3 actors can access this URL: Recruiter, Manager, Administrator.
//                        .requestMatchers(
//                                "/interview/createInterview", // UC17 create new interview schedule
//                                "/interview/editInterview", "/interview/editInterviewView", // UC20 edit interview details
//                                "/interview/cancelInterview" // UC21 cancel interview schedule
//                        )
//                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // Only Interviewer can access this URL.
//                        .requestMatchers(
//                                "/interview/submitResultView", "/interview/submitResult", // UC19 submit interview result
//                                "/interview/reminder" // UC22 reminder
//                        )
//                        .hasAnyRole(ROLE_INTERVIEWER)
//                        .anyRequest().authenticated()
//                )
//                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/login"))
//                        .accessDeniedHandler((request, response, authException) ->
//                                response.sendRedirect("/error")
//                        )
//                ).httpBasic(Customizer.withDefaults())
//                .build();
//    }

    @Bean
    public SecurityFilterChain offerFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/offer/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Except of Interviewer, others can access this URL
                        .requestMatchers(
                                "/offer/search",
                                "/offer/offerdetail", // UC26 view offer details
                                "/offer/editoffer", // UC25 edit offer
                                "/offer/export", // UC31 export offer
                                "/offer/canceloffer", // UC29 cancel offer
                                "/offer/mark", // UC28 update offer status from Candidate
                                "/offer/acceptoffer",
                                "/offer/declineoffer",
                                "/offer/edit",
                                "/offer/create",
                                "/offer/candidateOffer"
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)

                        // Only Recruiter can access this URL
                        .requestMatchers(
                                "/offer/offer", // UC23 view offer list for Recruiter
                                "/offer/createoffer" // UC24 create new offer
                        )
                        .hasAnyRole(ROLE_RECRUITER)

                        // Only Manager and Admin can access this URL
                        .requestMatchers(
                                "/offer/approveoffer", // UC27 Approve offer
                                "/offer/rejectoffer" // UC27 Reject offer
                        )
                        .hasAnyRole(ROLE_MANAGER, ROLE_ADMINISTRATOR)

                        // Only Manager can access this URL
                        .requestMatchers(
                                "/offer/managerOffer", // UC23 view offer list for Manager
                                "/offer/reminder" // UC30 reminder to take action on the offer
                        )
                        .hasAnyRole(ROLE_MANAGER)

                        .requestMatchers(
                                "/offer/adminOffer" // UC23 view offer list for Admin
                        )
                        .hasAnyRole(ROLE_ADMINISTRATOR)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/error")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    public SecurityFilterChain webSocketFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/websocket/**")
//                        .hasAnyRole("RECRUITER", "MANAGER", "ADMINISTRATOR")
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/login"))
//                        .accessDeniedHandler((request, response, accessDeniedException) ->
//                                response.sendRedirect("/error")
//                        )
//                )
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }

    @Bean
    public SecurityFilterChain candidateFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/candidate/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Full 4 actors can access this URL.
                        .requestMatchers(
                                "/candidate/list", // UC05 view list of candidates
                                "/candidate/details" // UC07 new candidate's details
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)

                        // Except of Interviewer, others can access this URL.
                        .requestMatchers(
                                "/candidate/create", // UC06 create a new candidate
                                "/candidate/edit", // UC08 edit candidate
                                "/candidate/delete", // UC09 delete candidate
                                "/candidate/ban" // UC10 ban candidate
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/error")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }
    // </editor-fold>

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
}
