package org.example.simple.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private boolean shutdown = false;
    private int port;
    HttpServer(int port){
        this.port = port;
    }
    //создадим метод в котором будет деражать логику получения и обработки запросов
    public void await() {
        //Эта штуковина позволит занять нам указанный порт и слушать его
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен на порту " + port);
        }catch(Exception e){
            e.printStackTrace();//тут похорошему добавить логирование
            System.exit(1);
        }

        while (!shutdown){
            try(    // тут мы садимся и ждем, когда к нам кто-то подлкючится, и когда к нам кто-то
                    //подключается мы сразу получаем к нему доступ
                    //у сокета есть несколько потоков, входящий от клиента и исходящий, то что мы шлем клиенту
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
            ){
                System.out.println("Получен входящий запрос ");
                Request request = new Request(inputStream);
                request.parse();
                Response response = new Response(outputStream);
                response.setRequest(request);
                if(request.getRequestURI().startsWith("/servlet/")){
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                    outputStream.flush();
                }else{
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    // проброс исключения в методе main как тут это фу-фу-фу так делать не надо
    //возможно я переделаю
    public static void main(String[] args){
        HttpServer httpServer = new HttpServer(8189);
        httpServer.await();
    }
}
