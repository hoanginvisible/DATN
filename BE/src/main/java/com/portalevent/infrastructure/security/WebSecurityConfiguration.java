package com.portalevent.infrastructure.security;

import com.portalevent.infrastructure.apiconstant.ActorConstants;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author thangncph26123
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/roles").permitAll()
                .requestMatchers(Constants.UrlPath.URL_API_APPROVER_MANAGEMENT + "/**").hasAuthority(ActorConstants.ACTOR_APPROVER)
                .requestMatchers(Constants.UrlPath.URL_API_ORGANIZER_MANAGEMENT + "/**").hasAnyAuthority(ActorConstants.ACTOR_ORGANIZER, ActorConstants.ACTOR_ADMINISTRATIVE)
                .requestMatchers(Constants.UrlPath.URL_API_PARTICIPANT + "/**").hasAnyAuthority(ActorConstants.ACTOR_PARTICIPANT, ActorConstants.ACTOR_ORGANIZER)
                .requestMatchers(Constants.UrlPath.URL_API_ADMINISTRATIVE + "/**").hasAuthority(ActorConstants.ACTOR_ADMINISTRATIVE)
                .requestMatchers(Constants.UrlPath.URL_API_HISTORY + "/**").hasAnyAuthority(ActorConstants.ACTOR_APPROVER, ActorConstants.ACTOR_ADMINISTRATIVE, ActorConstants.ACTOR_ORGANIZER);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}