package com.oauth.chapter9;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Author: Sanjay Ghosh
 *
 * Client callback endpoint. Validates state, performs the client-side token
 * request directly against /token, and then uses the resulting access token
 * against the protected /data endpoint.
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        String expectedState = session == null ? null : (String) session.getAttribute("oauth_state");
        String codeVerifier = session == null ? null : (String) session.getAttribute("oauth_code_verifier");
        String expectedRedirectUri = session == null ? null : (String) session.getAttribute("oauth_redirect_uri");

        String returnedState = request.getParameter("state");
        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (expectedState == null || returnedState == null || !expectedState.equals(returnedState)) {
            writeError(response, "Invalid state. The authorization response does not match the original request.");
            return;
        }

        if (error != null) {
            clearSession(session);
            writeError(response, "Authorization was not granted: " + error);
            return;
        }

        if (code == null || codeVerifier == null || expectedRedirectUri == null) {
            clearSession(session);
            writeError(response, "Missing authorization response data.");
            return;
        }

        String tokenResponse = exchangeCodeForToken(request, code, expectedRedirectUri, codeVerifier);
        String accessToken = jsonValue(tokenResponse, "access_token");
        String tokenError = jsonValue(tokenResponse, "error");

        if (accessToken == null) {
            clearSession(session);
            writeError(response, "Token exchange failed: " + (tokenError == null ? tokenResponse : tokenError));
            return;
        }

        String resourceResponse = callProtectedResource(request, accessToken);
        clearSession(session);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><head><title>OAuth Callback</title></head><body>");
        response.getWriter().println("<h2>OAuth 2.0 Client Callback</h2>");
        response.getWriter().println("<p>Authorization code received and exchanged successfully.</p>");
        response.getWriter().println("<p><b>Access token:</b> " + OAuthUtil.html(accessToken) + "</p>");
        response.getWriter().println("<p><b>Protected resource response:</b></p>");
        response.getWriter().println("<pre>" + OAuthUtil.html(resourceResponse) + "</pre>");
        response.getWriter().println("<p><a href='" + request.getContextPath() + "/login'>Run the flow again</a></p>");
        response.getWriter().println("</body></html>");
    }

    private String exchangeCodeForToken(HttpServletRequest request,
                                        String code,
                                        String redirectUri,
                                        String codeVerifier) throws IOException {
        String tokenUri = buildBaseUrl(request) + request.getContextPath() + "/token";
        String body = "grant_type=authorization_code"
                + "&code=" + encode(code)
                + "&redirect_uri=" + encode(redirectUri)
                + "&client_id=" + encode(OAuthDemoStore.CLIENT_ID)
                + "&code_verifier=" + encode(codeVerifier);

        HttpRequest tokenRequest = HttpRequest.newBuilder()
                .uri(URI.create(tokenUri))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            return httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString()).body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Token request was interrupted", e);
        }
    }

    private String callProtectedResource(HttpServletRequest request,
                                         String accessToken) throws IOException {
        String resourceUri = buildBaseUrl(request) + request.getContextPath() + "/data";
        HttpRequest dataRequest = HttpRequest.newBuilder()
                .uri(URI.create(resourceUri))
                .timeout(Duration.ofSeconds(5))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        try {
            HttpResponse<String> result = httpClient.send(
                    dataRequest, HttpResponse.BodyHandlers.ofString());
            return result.statusCode() + " " + result.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Resource request was interrupted", e);
        }
    }

    private String buildBaseUrl(HttpServletRequest request) {
        String origin = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            origin += ":" + request.getServerPort();
        }
        return origin;
    }

    private String jsonValue(String json, String key) {
        String marker = "\"" + key + "\"" + ":\"";
        int start = json.indexOf(marker);
        if (start < 0) {
            return null;
        }
        int valueStart = start + marker.length();
        int valueEnd = json.indexOf('"', valueStart);
        if (valueEnd < 0) {
            return null;
        }
        return json.substring(valueStart, valueEnd);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private void clearSession(HttpSession session) {
        if (session != null) {
            session.removeAttribute("oauth_state");
            session.removeAttribute("oauth_code_verifier");
            session.removeAttribute("oauth_redirect_uri");
        }
    }

    private void writeError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body><h2>OAuth Error</h2><p>"
                + OAuthUtil.html(message) + "</p></body></html>");
    }
}
