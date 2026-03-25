package com.oauth.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class TokenServlet
 */
@WebServlet("/token")
public class TokenServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    	String clientId = req.getParameter("client_id");
    	String clientSecret = req.getParameter("client_secret");

    	if (!"test".equals(clientId) || !"secret123".equals(clientSecret)) {
    	    resp.setStatus(401);
    	    resp.getWriter().write("Invalid client");
    	    return;
    	}
    	
    	
        String code = req.getParameter("code");

        if (!"abc123".equals(code)) {
            resp.setStatus(400);
            resp.getWriter().write("Invalid code");
            return;
        }

        // Simple JWT-like token (replace with real JWT later)
        String token = "header.payload.signature";

        resp.setContentType("application/json");
        resp.getWriter().write("{\"access_token\":\"" + token + "\"}");
    }
}