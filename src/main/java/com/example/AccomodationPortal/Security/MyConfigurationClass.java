package com.example.AccomodationPortal.Security;

import com.example.AccomodationPortal.Student.CustomFetchUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class MyConfigurationClass {

    private final CustomFetchUserDetails customFetchUserDetails;
    private final JwtOncePerRequest jwtOncePerRequestClass;
    private final PasswordEncoder passwordEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf
                        .disable())
                        .authenticationProvider(daoAuthenticationProvider)
                        .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/user/v1/authenticate").permitAll()
                                .requestMatchers("/student/v1/createAccount").permitAll()
                                .anyRequest().permitAll()
                        )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtOncePerRequestClass, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomFetchUserDetails customFetchUserDetails){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customFetchUserDetails);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

}
