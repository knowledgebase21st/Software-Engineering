package com.oauth.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class GetTokenServlet
 */
@WebServlet("/getToken")
public class GetTokenServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String code = req.getParameter("code");
        resp.setContentType("text/html"); 
        resp.getWriter().write(
            "<form method='post' action='/oauth/token'>" +
            "<input name='code' value='" + code + "'/><br/>" +
            "<input name='client_id' value='test'/><br/>" +
            "<input name='client_secret' value='secret123'/><br/>" +
            "<button type='submit'>Exchange Code for Token</button>" +
            "</form>"
        );
    }
}