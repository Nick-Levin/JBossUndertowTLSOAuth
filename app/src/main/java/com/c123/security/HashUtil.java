package com.c123.security;

import java.security.MessageDigest;

public final class HashUtil {

    private HashUtil() {}

    public static String hashString(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        byte[] result = md.digest(str.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
