package com.oauth.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CallbackServlet
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String code = req.getParameter("code");

        if (code == null) {
            resp.getWriter().write("Authorization failed");
            return;
        }
        resp.setContentType("text/html"); 
        resp.getWriter().write(
            "<h3>Authorization Code: " + code + "</h3>" +
            "<a href='/oauth/getToken?code=" + code + "'>Get Access Token</a>"
        );
    }
}