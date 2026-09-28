package com.oauth.chapter9;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Sanjay Ghosh
 *
 * Resource Server endpoint. Uses the opaque access token to find the
 * authorization information and enforces the required scope.
 */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleResourceRequest(request, response, "calendar.read");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleResourceRequest(request, response, "calendar.write");
    }

    private void handleResourceRequest(HttpServletRequest request,
                                       HttpServletResponse response,
                                       String requiredScope) throws IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            reject(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing or invalid Bearer access token.");
            return;
        }

        String token = authorization.substring("Bearer ".length()).trim();
        if (token.isEmpty()) {
            reject(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Missing access token.");
            return;
        }

        OAuthDemoStore.AccessTokenRecord tokenRecord =
                OAuthDemoStore.validateAccessToken(
                        token,
                        OAuthDemoStore.RESOURCE_AUDIENCE,
                        requiredScope
                );

        if (tokenRecord == null) {
            reject(response, HttpServletResponse.SC_FORBIDDEN,
                    "Access token is invalid, expired, or lacks the required scope: " + requiredScope);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{"
                + "\"message\":\"Protected calendar data returned\","
                + "\"user\":\"" + OAuthUtil.html(tokenRecord.user()) + "\","
                + "\"required_scope\":\"" + requiredScope + "\","
                + "\"granted_scope\":\"" + tokenRecord.scope() + "\""
                + "}");
    }

    private void reject(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{\"error\":\"access_denied\",\"message\":\""
                + OAuthUtil.html(message) + "\"}");
    }
}
