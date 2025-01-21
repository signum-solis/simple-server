package org.example.simple.http.server;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class Response implements HttpServletResponse {
    //сам запрос
    Request request;
    //исходящийй поток, т.е куда нам надо что-то отправлять
    OutputStream outputStream;
    //Штуковина которая позволяет нам что-то писать в исходящий поток
    PrintWriter printWriter;
    //найдет директорию где бы мы не запустили этот код
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    public void setRequest(Request request){
        this.request = request;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    public Response(OutputStream outputStream){
        this.outputStream = outputStream;
        this.printWriter = new PrintWriter(outputStream);
    }

    //метод, который позволит отправить в ответ какое-то сообщение
    public void sendStaticResource(){
        try {
            //определям име корневой папки и файл который у нас запросил пользователь
            File file = new File(WEB_ROOT, request.getRequestURI());
            if(!file.exists()){
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                                      "Content-Type: text/html\r\n" +
                                      "Content-Length: 23\r\n" + "\r\n" +
                                      "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());
            }
            try(FileInputStream fileInputStream = new FileInputStream(file)){
                String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + file.length() + "\r\n" +
                        "\r\n";
                outputStream.write(responseHeaders.getBytes());
                fileInputStream.transferTo(outputStream);
                outputStream.flush();
                outputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //=======  default ==========

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return "";
    }

    @Override
    public String encodeRedirectURL(String s) {
        return "";
    }

    @Override
    public String encodeUrl(String s) {
        return "";
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return "";
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String s, String s1) {

    }

    @Override
    public void addHeader(String s, String s1) {

    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return "";
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return List.of();
    }

    @Override
    public Collection<String> getHeaderNames() {
        return List.of();
    }

    @Override
    public String getCharacterEncoding() {
        return "";
    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

}
