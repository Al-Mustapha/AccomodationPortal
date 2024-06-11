package com.example.AccomodationPortal.Security;

import com.example.AccomodationPortal.Student.CustomFetchUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DaoAuthenticationProviderConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final CustomFetchUserDetails customFetchUserDetails;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customFetchUserDetails);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
