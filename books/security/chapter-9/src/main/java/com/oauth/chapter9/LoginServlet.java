package com.oauth.chapter9;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Author: Sanjay Ghosh
 *
 * Client-side entry point. Generates state and PKCE values, stores them in
 * the client session, and redirects the browser to the authorization endpoint.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String origin = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            origin += ":" + request.getServerPort();
        }

        String redirectUri = origin + request.getContextPath()
                + OAuthDemoStore.REGISTERED_REDIRECT_PATH;

        String state = OAuthUtil.randomToken(24);
        String codeVerifier = OAuthUtil.createCodeVerifier();
        String codeChallenge = OAuthUtil.createCodeChallenge(codeVerifier);

        HttpSession session = request.getSession(true);
        session.setAttribute("oauth_state", state);
        session.setAttribute("oauth_code_verifier", codeVerifier);
        session.setAttribute("oauth_redirect_uri", redirectUri);

        String authorizeUri = request.getContextPath() + "/authorize"
                + "?response_type=code"
                + "&client_id=" + encode(OAuthDemoStore.CLIENT_ID)
                + "&redirect_uri=" + encode(redirectUri)
                + "&scope=" + encode("calendar.read calendar.write")
                + "&state=" + encode(state)
                + "&code_challenge=" + encode(codeChallenge)
                + "&code_challenge_method=S256";

        response.sendRedirect(authorizeUri);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
