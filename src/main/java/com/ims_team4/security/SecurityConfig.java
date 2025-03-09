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

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
// HoanTX
public class SecurityConfig implements Constants.Role {
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
     * @author HoanTX
     */
    @Bean
    public SecurityFilterChain loginLogoutFilterChain(HttpSecurity http,
                                                      UserDetailsService service,
                                                      DataSource source) throws Exception {
        return http
                .securityMatcher("/login", "/logout")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me"))
                .rememberMe(me -> me
                        .tokenRepository(persistentTokenRepository(source))
                        .tokenValiditySeconds(7 * 24 * 60 * 60)
                        .userDetailsService(service)
                        .alwaysRemember(false))
                .httpBasic(Customizer.withDefaults()).build();
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
                EmployeeDTO empDTO = empSrv.getEmployeeById(Math.toIntExact(userDTO.getId()));
                System.out.println(empDTO);
                if (empDTO != null) {
                    emp = EmployeeDTO.builder()
                            .password(empDTO.getPassword())
                            .email(email)
                            .role(empDTO.getRole())
                            .build();
                } else {
                    throw new Exception("Do not find because list is empty");
                }
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
                ).httpBasic(Customizer.withDefaults()).build();
    }
//
//    @Bean
//    public SecurityFilterChain jobFunctionalFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/jobs/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        // UC11 view job list
//                        .requestMatchers("/jobs/list").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // UC12 create new job
//                        .requestMatchers("/jobs/create").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC13 view job details
//                        .requestMatchers("/jobs/details").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // UC14 edit job
//                        .requestMatchers("/jobs/edit").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC15 delete job
//                        .requestMatchers("/jobs/delete").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults()).build();
//    }
//
//    // HoanTX
//    @Bean
//    public SecurityFilterChain interviewFunctionalFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/interview/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        // UC16 view interview schedule list
//                        .requestMatchers("/interview/list").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // UC17 create new interview schedule
//                        .requestMatchers("/interview/createInterview").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC18 view interview schedule details
//                        .requestMatchers("/interview/interviewDetail").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // UC19 submit interview result
//                        .requestMatchers("/interview/submit").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC20 edit interview details
//                        .requestMatchers("/interview/editInterview").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults()).build();
//    }
//
////    @Bean
////    public SecurityFilterChain offerFunctionalFilterChain(HttpSecurity http) throws Exception {
////        return http
////                .securityMatcher("/interview/**")
////                .csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(authorize -> authorize
////                        // UC16 view interview schedule list
////                        .requestMatchers("/interview/list").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
////
////                        // UC17 create new interview schedule
////                        .requestMatchers("/interview/createInterview").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
////
////                        // UC18 view interview schedule details
////                        .requestMatchers("/interview/interviewDetail").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
////
////                        // UC19 submit interview result
////                        .requestMatchers("/interview/submit").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
////
////                        // UC20 edit interview details
////                        .requestMatchers("/interview/editInterview").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
////                        .anyRequest().authenticated()
////                )
////                .httpBasic(Customizer.withDefaults()).build();
////    }
//
//    @Bean
//    public SecurityFilterChain candidateFunctionalFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher("/candidate/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        // UC05 view list of candidates
//                        .requestMatchers("/candidate/list").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // UC06 create a new candidate
//                        .requestMatchers("/candidate/create").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC07 new candidate's details
//                        .requestMatchers("/candidate/details").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
//
//                        // UC08 edit candidate
//                        .requestMatchers("/candidate/edit").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC09 delete candidate
//                        .requestMatchers("/candidate/delete").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//
//                        // UC10 ban candidate
//                        .requestMatchers("/candidate/ban").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
//                        .anyRequest().authenticated()
//                ).httpBasic(Customizer.withDefaults()).build();
//    }
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
