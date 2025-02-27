package com.ims_team4.utils;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class RandomCode {
    @NotNull
    public static String generateSixRandomCodes() {
        SecureRandom random = new SecureRandom();
        int code = 1000 + random.nextInt(100);
        return String.valueOf(code);
    }
}
