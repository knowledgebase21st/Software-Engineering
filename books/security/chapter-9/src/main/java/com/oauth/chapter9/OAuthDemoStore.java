package com.oauth.chapter9;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Sanjay Ghosh
 *
 * In-memory storage used by the educational OAuth 2.0 simulation.
 * A production authorization server would use appropriate persistent
 * and/or distributed storage and stronger lifecycle controls.
 */
public final class OAuthDemoStore {

    public static final String CLIENT_ID = "chapter9-client";
    public static final String REGISTERED_REDIRECT_PATH = "/callback";
    public static final String GRANTED_SCOPE = "calendar.read";
    public static final String RESOURCE_AUDIENCE = "calendar-api";

    private static final long AUTHORIZATION_CODE_LIFETIME_SECONDS = 60;
    private static final long ACCESS_TOKEN_LIFETIME_SECONDS = 300;

    private static final Map<String, AuthorizationCodeRecord> AUTHORIZATION_CODES =
            new ConcurrentHashMap<>();

    private static final Map<String, AccessTokenRecord> ACCESS_TOKENS =
            new ConcurrentHashMap<>();

    private OAuthDemoStore() {
    }

    public static void storeAuthorizationCode(String code,
                                               String clientId,
                                               String redirectUri,
                                               String user,
                                               String scope,
                                               String codeChallenge) {
        Instant now = Instant.now();
        AUTHORIZATION_CODES.put(code, new AuthorizationCodeRecord(
                clientId,
                redirectUri,
                user,
                scope,
                codeChallenge,
                now,
                now.plusSeconds(AUTHORIZATION_CODE_LIFETIME_SECONDS)
        ));
    }

    /**
     * Redeems an authorization code exactly once.
     * The synchronized block keeps validation + consumption atomic for this demo.
     */
    public static synchronized AuthorizationCodeRecord redeemAuthorizationCode(
            String code,
            String clientId,
            String redirectUri,
            String codeVerifier) {

        AuthorizationCodeRecord record = AUTHORIZATION_CODES.get(code);
        if (record == null || record.expiresAt().isBefore(Instant.now())) {
            return null;
        }

        if (!record.clientId().equals(clientId)
                || !record.redirectUri().equals(redirectUri)
                || !OAuthUtil.verifyPkce(codeVerifier, record.codeChallenge())) {
            return null;
        }

        AUTHORIZATION_CODES.remove(code);
        return record;
    }

    public static String issueAccessToken(String clientId,
                                          String user,
                                          String scope) {
        String token = OAuthUtil.randomToken(32);
        Instant now = Instant.now();
        ACCESS_TOKENS.put(token, new AccessTokenRecord(
                clientId,
                user,
                RESOURCE_AUDIENCE,
                scope,
                now,
                now.plusSeconds(ACCESS_TOKEN_LIFETIME_SECONDS)
        ));
        return token;
    }

    public static AccessTokenRecord validateAccessToken(String token,
                                                         String requiredAudience,
                                                         String requiredScope) {
        AccessTokenRecord record = ACCESS_TOKENS.get(token);
        if (record == null) {
            return null;
        }

        if (record.expiresAt().isBefore(Instant.now())) {
            ACCESS_TOKENS.remove(token);
            return null;
        }

        if (!record.audience().equals(requiredAudience)
                || !OAuthUtil.scopeContains(record.scope(), requiredScope)) {
            return null;
        }

        return record;
    }

    public static String registeredRedirectUri(String origin) {
        return origin + REGISTERED_REDIRECT_PATH;
    }

    public static long getAuthorizationCodeLifetimeSeconds() {
        return AUTHORIZATION_CODE_LIFETIME_SECONDS;
    }

    public static long getAccessTokenLifetimeSeconds() {
        return ACCESS_TOKEN_LIFETIME_SECONDS;
    }

    public record AuthorizationCodeRecord(
            String clientId,
            String redirectUri,
            String user,
            String scope,
            String codeChallenge,
            Instant issuedAt,
            Instant expiresAt) {
    }

    public record AccessTokenRecord(
            String clientId,
            String user,
            String audience,
            String scope,
            Instant issuedAt,
            Instant expiresAt) {
    }
}
