package org.example.simple.http.server;


//Задача этого класса получить какие-то данные, получить запрос-ответ и по какой-то логике их обработать
public class StaticResourceProcessor {
    public void process(Request request, Response response){
        response.sendStaticResource();
    }
}
