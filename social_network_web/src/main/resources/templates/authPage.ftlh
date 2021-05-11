<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auth</title>
    <link rel="stylesheet" href="#">
    <style>
        body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: papayawhip;
            display: flex;
            flex-direction: column;
            align-items: center;
            align-content: center;
            padding-top: 200px;
        }

        .reg_form {
            display: flex;
            flex-direction: column;
            align-content: space-around;
            padding: 10px;
            align-items: center;
            background-color: #90D1C4;
            margin-bottom: 85px;
            width: 100%;
            font-size: 20px;
            max-width: 700px;
            border-radius: 3px;
        }

        input {
            width: 150px;
            height: 25px;
        }

        .text-danger {
            color: red;
        }
    </style>
</head>
<body>
<form class="form" method="post" action="/auth" autocomplete="off">
    <div class="reg_form">

        <label for="email">Username</label>
        <input id="email" name="email" type="text" data-lpignore="true"/>


        <label for="password">Password</label>

        <input id="password" name="password" type="password" data-lpignore="true"/>

        <label>
            <input type="checkbox" name="remember-me">Запомни меня
        </label>
    </div>

    <button class="btn btn--primary" type="submit">Войти</button>

    <a class="nav-link" href="/oauth2/authorization/google">Гугл</a>
    <a href="/registration"> Зарегистрироваться в Talker-e</a>
    
</form>
</body>
</html>