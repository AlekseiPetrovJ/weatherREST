REST сервис по регистрации погодных данных
===============================

## REST API спроектирован и разработан на стеке:

- JPA. Hibernate (Postgres SQL, H2 SQL)
- Spring Boot 3
- Spring validator
- Spring MVC
- Maven
- Spring security (в том числе @PreAythorize)

## API реализует следующие возможности:

### Пользователи без авторизации:

1) Login/logout
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

### <a href="WeatherREST.postman_collection.json">Postman JSON</a>

The REST API to the example app is described below.

## Get html for authorization

### Request

`GET /auth/login/`

    curl --location 'http://localhost:8080/auth/login'

### Response
    Content-Type: text/html;charset=UTF-8 
    Status: 200 OK

<details>
<summary>Подробнее ...</summary>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>

<form name="f" method="post" action="/process_login">

<label for="username">Введите имя пользователя: </label>
<input type="text" name="username" id="username"/>
<br/>
<label for="password">Введите пароль: </label>
<input type="password" name="password" id="password"/>
<br/>
<input type="submit" value="Login"/>


</form>

</body>
</html>
</details>
    

## Login

### Request

`POST /process_login`

    curl --location 'http://localhost:8080/process_login' \
    --form 'username="admin"' \
    --form 'password="123123"'

### Response
    Content-Type: text/html;charset=UTF-8 
    Status: 200 OK

<details>
<summary>Подробнее ...</summary>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logout</title>
</head>
<body>
<form action="/logout" method="POST">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
</details>

## Logout

### Request

`POST /logout`

    curl --location 'http://localhost:8080/logout' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=AE5E56044E4189B40F065F8E60F5204E' \
    --data '
    '

### Response
    Content-Type: text/html;charset=UTF-8 
    Status: 200 OK

<details>
<summary>Подробнее ...</summary>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<title>Login page</title>
</head>

<body>

<form name="f" method="post" action="/process_login">

<label for="username">Введите имя пользователя: </label>
<input type="text" name="username" id="username"/>
<br/>
<label for="password">Введите пароль: </label>
<input type="password" name="password" id="password"/>
<br/>
<input type="submit" value="Login"/>


</form>

</body>

</html>
</details>


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

    curl --location 'http://localhost:8080/measurements' 

### Response

    Content-Type: application/json
    Status: 200 OK

    [{"value":25.0,"raining":false,"sensor":{"name":"New York 1"}},{"value":28.7,"raining":false,"sensor":{"name":"New York 1"}},{"value":29.0,"raining":false,"sensor":{"name":"New York 1"}},{"value":-25.0,"raining":true,"sensor":{"name":"New York 2"}},{"value":10.0,"raining":false,"sensor":{"name":"Moscow 1"}}]

## Get rainy measurements

### Request

`GET /measurements`

    curl --location 'http://localhost:8080/measurements/rainyDaysCount' \
    --header 'Cookie: JSESSIONID=AE5E56044E4189B40F065F8E60F5204E' 

### Response

    Content-Type: application/json
    Status: 200 OK

    2

## Add sensors

### Request

`POST /sensors/registration`

    curl --location 'http://localhost:8080/sensors/registration' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=AE5E56044E4189B40F065F8E60F5204E' \
    --data '{
    "name": "Ufa 22"
} 

### Response

    Content-Type: application/json
    Status: 200 OK

    "OK"

## Add measurement

### Request

`POST /measurements/add`

    curl --location 'http://localhost:8080/measurements/add' \
    --header 'Content-Type: application/json' \
    --header 'Cookie: JSESSIONID=AE5E56044E4189B40F065F8E60F5204E' \
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