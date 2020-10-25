# HtmlParser
Чтобы запустить приложение нужно:
1. Склонировать репозиторий с проектом.
2. Выбрать JDK 8.
3. Запустить Maven для загрузки всех зависимостей.
4. Установить драйвер PostgreSQL.
5. Подключится к базе данных.

         url: jdbc:postgresql://localhost:5432/HPDB
        
         database name: HPDB
     
         port: 5432
     
         username: postgres
    
         password: postgres
         
6. Запустить класс HtmlParserApplication

         ru/savdieyong/HtmlParser/HtmlParserApplication.java
         
7. Ссылки: 
          
          localhost:8080/pages - все страницы из базы данных
          localhost:8080/parser - добавление новой страницы в базу данных, подсчет количества уникальных слов на странице
          localhost:8080/words/{page_id} - таблица уникальных слов с количеством повторов на странице {page_id}
