package com.company.hotel_booking;

import com.company.hotel_booking.web.errorhandlers.ErrorAccessDeniedHandler;
import com.company.hotel_booking.web.usersSecurity.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Class Spring configuration for security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Session Management
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .invalidSessionUrl("/login?timeout=true")
                .maximumSessions(1).maxSessionsPreventsLogin(true).and()
                .and()

                //Cross Site Request Forgery
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()

                //Cross Origin Resource Sharing
                .cors()
                .and()

                //Clickjacking mitigation
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()

                //Authentication/Authorization filtering for URL/HTTP methods
                .authorizeRequests()
                .mvcMatchers("/", "/login*", "/error/**", "/rooms/search_available_rooms",
                        "/reservations/booking", "/rooms/rooms_available", "/reservations/clean_booking",
                        "/reservations/delete_booking/**", "/users/create", "/language",
                        "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .mvcMatchers(HttpMethod.POST, "/users/create", "/rooms/search_available_rooms",
                        "/rooms/rooms_available", "/reservations/add_booking").permitAll()

                .mvcMatchers(HttpMethod.GET, "/users/**", "/rooms/**", "/reservations/**", "/api/**")
                .authenticated()
                .mvcMatchers(HttpMethod.POST, "/users/**", "/rooms/**", "/reservations/**", "/api/**")
                .authenticated()
                .mvcMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .mvcMatchers(HttpMethod.DELETE, "/api/**").authenticated()

                .anyRequest().denyAll()
                .and()

                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()

                //Username/Password login via Basic auth
                .httpBasic()
                .and()

                //Username/Password login via Form-based auth
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .authenticationProvider(authProvider())

                //Logout configuration
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()

                //Build Security Filter Chain object
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new ErrorAccessDeniedHandler();
    }
}