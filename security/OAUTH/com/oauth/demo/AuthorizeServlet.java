package com.oauth.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AuthorizeServlet
 */
@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	resp.setContentType("text/html"); 
    	resp.getWriter().write(
            "<form method='post'>" +
            "User: <input name='username'/><br/>" +
            "Pass: <input name='password' type='password'/><br/>" +
            "<button name='action' value='allow'>Allow</button>" +
            "<button name='action' value='deny'>Deny</button>" +
            "<input type='hidden' name='redirect_uri' value='" + req.getParameter("redirect_uri") + "'/>" +
            "</form>"
        );
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getParameter("action");
        String redirectUri = req.getParameter("redirect_uri");

        if ("deny".equals(action)) {
            resp.sendRedirect(redirectUri + "?error=access_denied");
            return;
        }

        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        if ("admin".equals(user) && "password".equals(pass)) {
            String code = "abc123"; // normally random + stored
            resp.sendRedirect(redirectUri + "?code=" + code);
        } else {
            resp.getWriter().write("Invalid login");
        }
    }
}