package org.example.simple.http.server;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

//Класс для того, чтобы работать не с байтами, не со строкой, а с готовым хорошим объектом
public class Request implements HttpServletRequest {
    //Запрос мы формируем из входящего потока
    private InputStream input;
    private String uri;
    private Map<String, String> parameters;

    @Override
    public String getRequestURI(){
        return  uri;
    }

    @Override
    public String getParameter(String s) {
        return parameters.get(s);
    }


    //когда мы запрос формируем, тогда мы ему этот входящий поток присваиваем
    public Request(InputStream input){
        this.input = input;
    }
    //те байты что к нам пришли, мы хотим распарсить и получить какую-то понятную структуру
    public void parse(){
        try {

            //Мы можем из тех байтов что получили собрать какую-то строку
            StringBuilder stringBuilder = new StringBuilder(4096);
            int n;
            byte[] buffer = new byte[4096];//получаем данные от пользователя
            try {
                n = input.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                n = -1;//если мы ничего не прочитали, то никаких данных у нас по сути и нет
            }
            //Потихоньку собираем из нашего потока строку
            for (int i = 0; i < n; i++) {
                stringBuilder.append((char) buffer[i]);
            }
            String requestString = stringBuilder.toString();
            uri = parseUri(requestString);
            parameters = parseParameters(requestString);
            System.out.print(requestString);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String parseUri(String requestString){
        if (requestString == null) return null; // проверка на null
        int index1 = requestString.indexOf(' '); // ищем первый пробел
        if (index1 == -1) return null; // если пробела нет
        int index2 = requestString.indexOf(' ', index1 + 1); // ищем второй пробел
        if (index2 == -1) index2 = requestString.length(); // если второго пробела нет, берем длину строки
        System.out.println(requestString.substring(index1 + 1, index2));
        String con = requestString.substring(index1 + 1, index2);
        if(con.contains("?")){
            System.out.println(con.substring(0, con.indexOf("?")));
            return con.substring(0, con.indexOf("?"));
        }else {
            return requestString.substring(index1 + 1, index2);
        }
    }


    // /add?a=10&b=Hello.. надо из запроса достать эту строку
    // делаем упрощенную версию работаем только с GET запросом
    private Map<String, String> parseParameters(String requestString){
        Map<String, String> out = new HashMap<>();//готовим мапу, которую вернем
        int index1 = requestString.indexOf("?");//находим знак вопроса в запросе
        if(index1 == -1){
            return Collections.emptyMap();
        }//если не находим говорим, что параметров ни одного нет
        int index2 = requestString.indexOf(' ', index1 + 1);//находим знак пробела, чтобы найти a=10&b=Hello
        if(index2 == -1){
            return Collections.emptyMap(); //по хорошему это исключение, но пока напишем так
        }
        //в стриме находим нужную строку и разбиваем ее по амперсанду на набор строк
        Arrays.stream(
                requestString.substring(index1 + 1, index2)
                        .split("&"))
                        .map(param -> param.split("="))
                        .forEach(
                                    keyValue -> {
                                        out.put(keyValue[0], keyValue[1]);
                                    }
                        );
        return Collections.unmodifiableMap(out);//оборачиваем так, чтобы мапу никто не смог поменять
    }


    //===== default ====
    @Override
    public String getAuthType() {
        return "";
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return "";
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return "";
    }

    @Override
    public String getPathInfo() {
        return "";
    }

    @Override
    public String getPathTranslated() {
        return "";
    }

    @Override
    public String getContextPath() {
        return "";
    }

    @Override
    public String getQueryString() {
        return "";
    }

    @Override
    public String getRemoteUser() {
        return "";
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return "";
    }



    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return "";
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return "";
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String s, String s1) throws ServletException {

    }

    @Override
    public void logout() throws ServletException {

    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return List.of();
    }

    @Override
    public Part getPart(String s) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return "";
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Map.of();
    }

    @Override
    public String getProtocol() {
        return "";
    }

    @Override
    public String getScheme() {
        return "";
    }

    @Override
    public String getServerName() {
        return "";
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return "";
    }

    @Override
    public String getRemoteHost() {
        return "";
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return "";
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return "";
    }

    @Override
    public String getLocalAddr() {
        return "";
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
