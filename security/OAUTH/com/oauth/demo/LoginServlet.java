package com.oauth.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String redirect = "http://172.21.236.75:9090/oauth/authorize?" +
                "response_type=code&client_id=test&redirect_uri=http://172.21.236.75:9090/oauth/callback";

        resp.sendRedirect(redirect);
    }
}
