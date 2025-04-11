package com.ims_team4.utils;

import java.util.Base64;

// Duc Long
public class UrlIdEncoder {
    public static String encodeId(int id) {
        return Base64.getUrlEncoder().encodeToString(String.valueOf(id).getBytes());
    }

    public static int decodeId(String encodedId) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedId);
        return Integer.parseInt(new String(decodedBytes));
    }
}
