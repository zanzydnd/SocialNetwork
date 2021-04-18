<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
</head>
<body>
<#if existErr??>
    <p class="text-danger">
        ${existErr}
    </p>
</#if>
<form method="post">
    <input name="email" type="email" placeholder="email"
           value="<#if userForm??>${userForm.email}</#if>">
    <#if emailError??>
        <div class="invalid-feedback">
            ${emailError}
        </div>
    </#if>
    <input name="firstName" type="text" placeholder="firstname"
           value="<#if userForm??>${userForm.firstName}</#if>">
    <input name="lastName" type="text" placeholder="lastname"
        value="<#if userForm??>${userForm.lastName}</#if>"
    >
    <input name="about" type="text" placeholder="about"
    value="<#if userForm??>${userForm.about}</#if>">
    <input name="password" type="password" placeholder="password">
    <#if passwordsErrorMessage??>
        <p class="text-danger">
            ${passwordsErrorMessage}
        </p>
    </#if>
    <#if passwordError??>
        <div class="invalid-feedback">
            ${passwordError}
        </div>
    </#if>
    <input name="password2" type="password" placeholder="repeat_password">
    <#if password2Error??>
        <div class="invalid-feedback">
            ${password2Error}
        </div>
    </#if>
    <input type="date" name="dateOfBirth" placeholder="date of birth"
           value="<#if userForm??>${userForm.dateOfBirth}</#if>">
    <div>
        <button type="submit">Зарегистрироваться</button>
        <a href="">Уже есть аккаунт</a>
    </div>
</form>
</body>
</html>