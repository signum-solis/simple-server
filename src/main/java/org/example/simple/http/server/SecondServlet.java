package org.example.simple.http.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecondServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Запрос попал в SecondServlet");
        PrintWriter out = resp.getWriter();
        out.println("HTTP/1.1 200 Ok\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 50\r\n" + "\r\n" +
                "<h1>Second servlet hello!</h1>");
        out.flush();
        out.close();
    }

    @Override
    public void destroy(){
        System.out.println("Simple servlet destroy");
    }

    @Override
    public void init() throws ServletException{
        System.out.println("Simple servlet created!");
    }
}
