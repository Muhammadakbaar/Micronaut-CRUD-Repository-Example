package com.sample.config;

import io.micronaut.security.authentication.providers.PasswordEncoder;

import javax.inject.Singleton;

@Singleton
public class BCryptPasswordEncoder implements PasswordEncoder {

    org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
