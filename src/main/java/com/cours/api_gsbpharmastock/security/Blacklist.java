package com.cours.api_gsbpharmastock.security;

import java.util.HashSet;
import java.util.Set;

public class Blacklist {
    private static final Set<String> blacklistedTokens = new HashSet<>();

    public static void addToken(String token) {
        blacklistedTokens.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
