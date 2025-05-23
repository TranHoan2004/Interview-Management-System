package com.ims_team4.security;

import com.ims_team4.config.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// HoanTX
public class AuthorizationSecurityConfig implements Constants.Role {
    @Bean
    public SecurityFilterChain userFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/user/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
                                "/user/list",
                                "/user/create",
                                "/user/details/",
                                "/user/edit/",
                                "/user/activate/",
                                "/user/delete/"
                        ).hasRole(ROLE_ADMINISTRATOR) // UC32, UC33, UC34, UC35, UC36

                        .requestMatchers("/user/test").hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
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
                        .requestMatchers(
                                "/jobs/manager/list/",
                                "/jobs/manager/job-detail/",
                                "/jobs/manager/create-job/",
                                "/jobs/manager/edit-job/",
                                "/jobs/manager/delete-job/",
                                "/jobs/manager/import-job/"
                        ).hasRole(ROLE_MANAGER)

                        .requestMatchers(
                                "/jobs/admin/listAllJob",
                                "/jobs/admin/job-detail/"
                        ).hasRole(ROLE_ADMINISTRATOR)

                        .requestMatchers("/jobs/interview/listJob").hasRole(ROLE_INTERVIEWER)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain interviewFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/interview/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Full 4 actors can access this URL.
                        .requestMatchers(
                                "/interview/list", // UC16 view interview schedule list
                                "/interview/interviewDetail" // UC18 view interview schedule details
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)

                        // 3 actors can access this URL: Recruiter, Manager, Administrator.
                        .requestMatchers(
                                "/interview/createInterview", "/interview/createInterviewView", // UC17 create new interview schedule
                                "/interview/editInterview", "/interview/editInterviewView", "/interview/reminder", // UC20 edit interview details
                                "/interview/cancelInterview" // UC21 cancel interview schedule
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)

                        // Only Interviewer can access this URL.
                        .requestMatchers(
                                "/interview/submitResultView", "/interview/submitResult", // UC19 submit interview result
                                "/interview/reminder" // UC22 reminder
                        )
                        .hasAnyRole(ROLE_INTERVIEWER)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain offerFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/offer/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Except of Interviewer, others can access this URL
                        .requestMatchers(
                                "/offer/search",
                                "/offer/offerdetail/", // UC26 view offer details
                                "/offer/editoffer/", // UC25 edit offer
                                "/offer/export", // UC31 export offer
                                "/offer/canceloffer/", // UC29 cancel offer
                                "/offer/mark/", // UC28 update offer status from Candidate
                                "/offer/acceptoffer/",
                                "/offer/declineoffer/",
                                "/offer/edit",
                                "/offer/create",
                                "/offer/candidateOffer/"
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)

                        // Only Recruiter can access this URL
                        .requestMatchers(
                                "/offer/offer/", // UC23 view offer list for Recruiter
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
                                "/offer/managerOffer/", // UC23 view offer list for Manager
                                "/offer/reminder" // UC30 reminder to take action on the offer
                        )
                        .hasAnyRole(ROLE_MANAGER)

                        .requestMatchers(
                                "/offer/adminOffer/" // UC23 view offer list for Admin
                        )
                        .hasAnyRole(ROLE_ADMINISTRATOR)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain candidateFunctionalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/candidate/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Full 4 actors can access this URL.
                        .requestMatchers(
                                "/candidate/list", // UC05 view list of candidates
                                "/candidate/details/", "/candidate/download-cv/" // UC07 new candidate's details
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)

                        // Except of Interviewer, others can access this URL.
                        .requestMatchers(
                                "/candidate/add", // UC06 create a new candidate
                                "/candidate/edit", // UC08 edit candidate
                                "/candidate/delete/", // UC09 delete candidate
                                "/candidate/ban/", // UC10 ban candidate
                                "/candidate/send-invitation/"
                        )
                        .hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR)
                        .anyRequest().authenticated()
                )
                // When users try to access any URLs but haven't logged in yet, the system will redirect to /login.
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain notificationFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/notification/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author -> author
                        .requestMatchers(
                                "/notification/create",
                                "/notification/list",
                                "/notification/update/",
                                "/notification/details"
                        ).hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
                        .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain webSocketFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/topic/**", "/specific/**", "/app/**", "/ws/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author -> author
                        .requestMatchers(
                                "/topic/**",
                                "/specific/**",
                                "/app/**",
                                "/ws/**"
                        ).hasAnyRole(ROLE_RECRUITER, ROLE_MANAGER, ROLE_ADMINISTRATOR, ROLE_INTERVIEWER)
                        .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/login"))
                        .accessDeniedHandler((request, response, authException) ->
                                response.sendRedirect("/access_denied")
                        )
                ).httpBasic(Customizer.withDefaults())
                .build();
    }
}
