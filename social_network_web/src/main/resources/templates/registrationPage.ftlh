<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
</head>
<body>

<form method="post">
    <input name="username" type="text" placeholder="username">
    <input name="email" type="email" placeholder="email">
    <input name="firstName" type="text" placeholder="firstname">
    <input name="lastName" type="text" placeholder="lastname">
    <input name="about" type="text" placeholder="about">
    <input name="password" type="password" placeholder="password">
    <input type="date" name="dateOfBirth" placeholder="date of birth">
    <div>
        <button type="submit">Зарегистрироваться</button>
        <a href="">Уже есть аккаунт</a>
    </div>
</form>
</body>
</html>