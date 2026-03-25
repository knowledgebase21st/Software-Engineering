package com.oauth.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class DataServlet
 */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String auth = req.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            resp.setStatus(401);
            resp.getWriter().write("Unauthorized");
            return;
        }

        String token = auth.substring(7);

        if (!"header.payload.signature".equals(token)) {
            resp.setStatus(403);
            resp.getWriter().write("Invalid token");
            return;
        }

        resp.getWriter().write("Secure Data for user: admin");
    }
}