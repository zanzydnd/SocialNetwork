<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auth</title>
    <link rel="stylesheet" href="#">
</head>
<body>
<form class="form" method="post" action="/auth" autocomplete="off">
    <div class="form-inner">
        <h2>Войти в Talker</h2>
        <div class="input-wrapper">
            <label for="email">Username</label>
            <div class="input-group">
                <input id="email" name="email" type="text" data-lpignore="true"/>
            </div>
        </div>
        <div class="input-wrapper">
            <label for="password">Password</label>
            <div class="input-group">
                <input id="password" name="password" type="password" data-lpignore="true"/>
            </div>
            <label>
                <input type="checkbox" name="remember-me">Запомни меня
            </label>
        </div>
        <div class="btn-group">
            <button class="btn btn--primary" type="submit">Войти</button>
        </div>
        <a href="/registration"> Зарегистрироваться в Talker-e</a>
    </div>
</form>
</body>
</html>