package com.hungdt.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("page");

        if(query != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(query + ".jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.getWriter().println("<h1>Welcome to our website</h1>");
        }
    }


}
