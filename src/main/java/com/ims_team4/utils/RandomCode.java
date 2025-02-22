package com.ims_team4.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;

import java.security.SecureRandom;

public class RandomCode {
    @NotNull
    @Bean
    public String generateSixRandomCodes() {
        SecureRandom random = new SecureRandom();
        int code = 1000 + random.nextInt(100);
        return String.valueOf(code);
    }
}
