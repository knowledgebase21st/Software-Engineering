package com.oauth.chapter9;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

/**
 * Author: Sanjay Ghosh
 *
 * Utility functions for the educational OAuth 2.0 simulation.
 */
public final class OAuthUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private OAuthUtil() {
    }

    public static String randomToken(int byteLength) {
        byte[] bytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static String createCodeVerifier() {
        // 32 random bytes provide high entropy while keeping the demo readable.
        return randomToken(32);
    }

    public static String createCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
    }

    public static boolean verifyPkce(String codeVerifier, String expectedChallenge) {
        if (codeVerifier == null || expectedChallenge == null) {
            return false;
        }
        String actualChallenge = createCodeChallenge(codeVerifier);
        return MessageDigest.isEqual(
                actualChallenge.getBytes(StandardCharsets.US_ASCII),
                expectedChallenge.getBytes(StandardCharsets.US_ASCII)
        );
    }

    public static boolean scopeContains(String grantedScope, String requiredScope) {
        if (grantedScope == null || requiredScope == null) {
            return false;
        }
        Set<String> scopes = Set.of(grantedScope.trim().split("\\s+"));
        return scopes.contains(requiredScope);
    }

    public static String html(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
