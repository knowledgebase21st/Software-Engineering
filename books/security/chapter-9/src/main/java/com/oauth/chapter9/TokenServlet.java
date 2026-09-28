package com.oauth.chapter9;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Sanjay Ghosh
 *
 * Authorization Server token endpoint. Validates the authorization code,
 * client, redirect URI, and PKCE verifier, then issues an opaque access token.
 */
@WebServlet("/token")
public class TokenServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");

        String grantType = request.getParameter("grant_type");
        String code = request.getParameter("code");
        String redirectUri = request.getParameter("redirect_uri");
        String clientId = request.getParameter("client_id");
        String codeVerifier = request.getParameter("code_verifier");

        if (!"authorization_code".equals(grantType)
                || code == null
                || redirectUri == null
                || clientId == null
                || codeVerifier == null) {
            sendOAuthError(response, HttpServletResponse.SC_BAD_REQUEST, "invalid_request");
            return;
        }

        OAuthDemoStore.AuthorizationCodeRecord authorizationCode =
                OAuthDemoStore.redeemAuthorizationCode(code, clientId, redirectUri, codeVerifier);

        if (authorizationCode == null) {
            sendOAuthError(response, HttpServletResponse.SC_BAD_REQUEST, "invalid_grant");
            return;
        }

        String accessToken = OAuthDemoStore.issueAccessToken(
                clientId,
                authorizationCode.user(),
                authorizationCode.scope()
        );

        String json = "{"
                + "\"access_token\":\"" + accessToken + "\","
                + "\"token_type\":\"Bearer\","
                + "\"expires_in\":" + OAuthDemoStore.getAccessTokenLifetimeSeconds() + ","
                + "\"scope\":\"" + authorizationCode.scope() + "\""
                + "}";

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(json);
    }

    private void sendOAuthError(HttpServletResponse response,
                                int status,
                                String error) throws IOException {
        response.setStatus(status);
        response.getWriter().print("{\"error\":\"" + error + "\"}");
    }
}
