<h1>Упрощенный сервер на Java core</h1>
<h2>Написание простого сервера на Java core поможет</h2>

1. Лучше понять как работает Spring 
2. Лучше понять возможности языка 
3. Понять как работают вэб приложения написанные на Java 


Если мы пишем сервер то нам нужно:
1. Написать приложение способное ожидать, когда к нему подключаться - ожидать сетевое подключение.
2. Ожидать когда ему начнут отправлять какие-то данные и как минимум он должен уметь эти данные воспринять и как то обработать, затем вернуть ответ клиенту


Данная реализация сервера(упрощенная) может:
1. Принимать и обрабатывать запросы от клиента
2. Оправлять ответы 
3. Выводить html-файлы(отправлять статические ресурсы)
4. Использовать сервлеты(для отправки данных и складывания двух чисел)
