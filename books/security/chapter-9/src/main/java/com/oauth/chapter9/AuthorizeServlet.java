package com.oauth.chapter9;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Author: Sanjay Ghosh
 *
 * Authorization Server authorization endpoint. This educational implementation
 * authenticates a demo user, shows a grant/deny page, and issues a short-lived
 * single-use authorization code.
 */
@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!validAuthorizationRequest(request)) {
            writeError(response, "Invalid authorization request.");
            return;
        }

        String state = request.getParameter("state");
        String redirectUri = request.getParameter("redirect_uri");
        String clientId = request.getParameter("client_id");
        String codeChallenge = request.getParameter("code_challenge");
        String scope = request.getParameter("scope");

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><head><title>OAuth Authorization</title></head><body>");
        response.getWriter().println("<h2>OAuth 2.0 Authorization Server</h2>");
        response.getWriter().println("<p><b>Client:</b> " + OAuthUtil.html(clientId) + "</p>");
        response.getWriter().println("<p><b>Requested scope:</b> " + OAuthUtil.html(scope) + "</p>");
        response.getWriter().println("<p>For this educational demo, the server grants only <b>calendar.read</b>.</p>");
        response.getWriter().println("<form method='post' action='" + request.getContextPath() + "/authorize'>");
        response.getWriter().println(hidden("response_type", request.getParameter("response_type")));
        response.getWriter().println(hidden("client_id", clientId));
        response.getWriter().println(hidden("redirect_uri", redirectUri));
        response.getWriter().println(hidden("scope", scope));
        response.getWriter().println(hidden("state", state));
        response.getWriter().println(hidden("code_challenge", codeChallenge));
        response.getWriter().println(hidden("code_challenge_method", "S256"));
        response.getWriter().println("<p>Demo username: <b>admin</b></p>");
        response.getWriter().println("<p>Demo password: <b>password</b></p>");
        response.getWriter().println("<label>Username: <input name='username' required></label><br><br>");
        response.getWriter().println("<label>Password: <input type='password' name='password' required></label><br><br>");
        response.getWriter().println("<button type='submit' name='action' value='allow'>Allow</button>");
        response.getWriter().println("<button type='submit' name='action' value='deny'>Deny</button>");
        response.getWriter().println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!validAuthorizationRequest(request)) {
            writeError(response, "Invalid authorization request.");
            return;
        }

        String state = request.getParameter("state");
        String redirectUri = request.getParameter("redirect_uri");
        String action = request.getParameter("action");

        if (!"allow".equals(action)) {
            redirectError(response, redirectUri, "access_denied", state);
            return;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!"admin".equals(username) || !"password".equals(password)) {
            writeError(response, "Authentication failed. Use the demo credentials shown on the page.");
            return;
        }

        String authorizationCode = OAuthUtil.randomToken(32);
        OAuthDemoStore.storeAuthorizationCode(
                authorizationCode,
                request.getParameter("client_id"),
                redirectUri,
                username,
                OAuthDemoStore.GRANTED_SCOPE,
                request.getParameter("code_challenge")
        );

        String callback = redirectUri
                + "?code=" + URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8)
                + "&state=" + URLEncoder.encode(state, StandardCharsets.UTF_8);

        response.sendRedirect(callback);
    }

    private boolean validAuthorizationRequest(HttpServletRequest request) {
        String responseType = request.getParameter("response_type");
        String clientId = request.getParameter("client_id");
        String redirectUri = request.getParameter("redirect_uri");
        String codeChallenge = request.getParameter("code_challenge");
        String method = request.getParameter("code_challenge_method");

        if (!"code".equals(responseType)
                || !OAuthDemoStore.CLIENT_ID.equals(clientId)
                || redirectUri == null
                || codeChallenge == null
                || !"S256".equals(method)) {
            return false;
        }

        String expected = expectedRedirectUri(request);
        return expected.equals(redirectUri);
    }

    private String expectedRedirectUri(HttpServletRequest request) {
        String origin = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            origin += ":" + request.getServerPort();
        }
        return origin + request.getContextPath() + OAuthDemoStore.REGISTERED_REDIRECT_PATH;
    }

    private void redirectError(HttpServletResponse response,
                               String redirectUri,
                               String error,
                               String state) throws IOException {
        String location = redirectUri + "?error="
                + URLEncoder.encode(error, StandardCharsets.UTF_8)
                + "&state=" + URLEncoder.encode(state == null ? "" : state, StandardCharsets.UTF_8);
        response.sendRedirect(location);
    }

    private String hidden(String name, String value) {
        return "<input type='hidden' name='" + OAuthUtil.html(name)
                + "' value='" + OAuthUtil.html(value) + "'>";
    }

    private void writeError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body><h2>OAuth Error</h2><p>"
                + OAuthUtil.html(message) + "</p></body></html>");
    }
}
