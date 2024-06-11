package com.example.AccomodationPortal.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderClass {

    @Bean
    public PasswordEncoder passwordEncoder(){
//        DelegatingPasswordEncoder del =
//                (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        BCryptPasswordEncoder bc = new BCryptPasswordEncoder(10);
//        del.setDefaultPasswordEncoderForMatches(bc);
        return new BCryptPasswordEncoder(10);
    }
}
