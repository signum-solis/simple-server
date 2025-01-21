package org.example.simple.http.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Запрос попал в SimpleServlet");
        PrintWriter out = resp.getWriter();
        out.println("HTTP/1.1 200 Ok\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 250\r\n" + "\r\n" +
                "<h1>Simple Servlet hello!</h1>");
        out.println("You parametrs ABC = " + req.getParameter("abc"));
        out.flush();
        out.close();
    }

    @Override
    public void destroy(){
        System.out.println("Second servlet destroy");
    }

    @Override
    public void init() throws ServletException{
        System.out.println("Second servlet created!");
    }
}
