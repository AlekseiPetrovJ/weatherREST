[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ec2e165f73844a95b9f24047685fc29c)](https://app.codacy.com/gh/AlekseiPetrovJ/weatherREST/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)


# REST сервис по регистрации погодных данных

## REST API спроектирован и разработан на стеке:

-   JPA. Hibernate (H2 SQL)
-   Spring Boot 3
-   Spring validator
-   Spring MVC
-   Maven
-   Spring security (including @PreAythorize)
-   Authentication with JSON Web Token (JWT)
-   Swagger (OpenAPI)
-   Logback

## API реализует следующие возможности:

### Пользователи без авторизации:

1)  Login
2)  Получение списка всех датчиков

### Роль USER дополнительно получает:

1)  Получение всех измерений
2)  Регистрация измерения

-   _Значение "value" должно быть не пустым и находиться в диапазоне от -100 до 100._
-   _Значение "raining" должно быть не пустым._
-   _Значение "sensor" должно быть не пустым. При этом, название датчика должно валидироваться в БД.
Датчик с таким названием должен быть зарегистрирован в системе (должен быть в БД).
Если такого датчика нет в БД - выдавать ошибку_

3)  Получение количества дождливых измерений

### Роль ADMIN дополнительно получает:

1)  Регистрация датчика

-   _Проверяется то, что датчика с таким названием еще нет в БД.
Если датчик с таким названием есть в БД - возвращается клиенту сообщение с ошибкой._
-   _Также, если название сенсора пустое или содержит менее 3 или более 30 символов,
клиенту возвращаться сообщение с ошибкой_


# REST API

## Credentials:
login: admin  
password: 123123

login: user  
password: 123123

## OpenAPI
<a href="http://localhost:8080/swagger-ui/index.html">Swagger UI</a>