[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ec2e165f73844a95b9f24047685fc29c)](https://app.codacy.com/gh/AlekseiPetrovJ/weatherREST/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

REST сервис по регистрации погодных данных
===============================

## REST API спроектирован и разработан на стеке:

- JPA. Hibernate (Postgres SQL, H2 SQL)
- Spring Boot 3
- Spring validator
- Spring MVC
- Maven
- Spring security (including @PreAythorize)
- Authentication with JSON Web Token (JWT)
- Logback

## API реализует следующие возможности:

### Пользователи без авторизации:

1) Login
2) Получение списка всех датчиков

### Роль USER дополнительно получает:

1) Получение всех измерений
2) Регистрация измерения

- _Значение "value" должно быть не пустым и находиться в диапазоне от -100 до 100._
- _Значение "raining" должно быть не пустым._
- _Значение "sensor" должно быть не пустым. При этом, название датчика должно валидироваться в БД.
Датчик с таким названием должен быть зарегистрирован в системе (должен быть в БД).
Если такого датчика нет в БД - выдавать ошибку_

5) Получение количества дождливых измерений

### Роль ADMIN дополнительно получает:

1) Регистрация датчика

- _Проверяется то, что датчика с таким названием еще нет в БД.
Если датчик с таким названием есть в БД - возвращается клиенту сообщение с ошибкой._
- _Также, если название сенсора пустое или содержит менее 3 или более 30 символов,
клиенту возвращаться сообщение с ошибкой_


# REST API

### Credentials:
login: admin  
password: 123123

login: user  
password: 123123

### <a href="WeatherREST.postman_collection.json">Postman JSON</a>

The REST API to the example app is described below.

## Login

### Request

`POST /auth/login`
    
    curl --location 'http://localhost:8080/auth/login' \
    --header 'Content-Type: application/json' \
    --data '{
    "username": "admin",
    "password": "123123"
    }'

### Response

    Status: 200 OK

`{
"jwt-token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjgzNzgyMzk3LCJpc3MiOiJ3ZWF0aGVyUkVTVCIsImV4cCI6MTY4Mzc4NTk5N30.Kg6s4enSAyEwAKbTd3NOjKTfogsAaiHR4dKeBnRJ7us"
}`

## Get list sensors

### Request

  `GET /sensors`

    curl --location 'http://localhost:8080/sensors' 

### Response

    Content-Type: application/json
    Status: 200 OK

    [{"name":"New York 1"},{"name":"New York 2"},{"name":"Moscow 1"},{"name":"London 1"}]

## Get measurements

### Request

`GET /measurements`

    curl --location 'http://localhost:8080/measurements' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjgzNzgxMjg3LCJpc3MiOiJwZXRyb3YiLCJleHAiOjE2ODM3ODQ4ODd9.jz7LbsYMv4xbVGwxSBz7bMcCXF285n-HXw6WqlzP4bE'

### Response

    Content-Type: application/json
    Status: 200 OK

    [{"value":25.0,"raining":false,"sensor":{"name":"New York 1"}},{"value":28.7,"raining":false,"sensor":{"name":"New York 1"}},{"value":29.0,"raining":false,"sensor":{"name":"New York 1"}},{"value":-25.0,"raining":true,"sensor":{"name":"New York 2"}},{"value":10.0,"raining":false,"sensor":{"name":"Moscow 1"}}]

## Get rainy measurements

### Request

`GET /measurements/rainyDaysCount`

    curl --location 'http://localhost:8080/measurements/rainyDaysCount' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjgzNzgxMjg3LCJpc3MiOiJwZXRyb3YiLCJleHAiOjE2ODM3ODQ4ODd9.jz7LbsYMv4xbVGwxSBz7bMcCXF285n-HXw6WqlzP4bE'

### Response

    Content-Type: application/json
    Status: 200 OK

    2

## Add sensors

### Request

`POST /sensors/registration`

    curl --location 'http://localhost:8080/sensors/registration' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjgzNzgxMjg3LCJpc3MiOiJwZXRyb3YiLCJleHAiOjE2ODM3ODQ4ODd9.jz7LbsYMv4xbVGwxSBz7bMcCXF285n-HXw6WqlzP4bE' \
    --header 'Content-Type: application/json' \
    --data '{
    "name": "Ufa 22"
    }
    '

### Response

    Content-Type: application/json
    Status: 200 OK

    "OK"

## Add measurement

### Request

`POST /measurements/add`

    curl --location 'http://localhost:8080/measurements/add' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNjgzNzgxMjg3LCJpc3MiOiJwZXRyb3YiLCJleHAiOjE2ODM3ODQ4ODd9.jz7LbsYMv4xbVGwxSBz7bMcCXF285n-HXw6WqlzP4bE' \
    --header 'Content-Type: application/json' \
    --data '{
    "value": 10,
    "raining": 1,
    "sensor": {
    "name": "Ufa 22"
    }
    }
    '

### Response

    Content-Type: application/json
    Status: 200 OK

    "OK"