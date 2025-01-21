package org.example.simple.http.server;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {
    public void process(Request request, Response response){
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        //работаем с рефлексией
        URLClassLoader loader = null;
        try {
           URL[] urls = new URL[1];
           URLStreamHandler streamHandler = null;
           String repository = new URL(
                   "file",
                   null,
                   (new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getCanonicalPath()+File.separator).toString()
           ).toString();
           urls[0] = new URL(null, repository, streamHandler);
           loader = new URLClassLoader(urls);

        }catch (IOException e){
            e.printStackTrace();
        }

        Class myClass = null;
        try {
            final String fullClassName = this.getClass().getPackage().getName() + "." + servletName;
            myClass = loader.loadClass(fullClassName);
            Servlet servlet =(Servlet) myClass.getDeclaredConstructor().newInstance();
            servlet.service(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
