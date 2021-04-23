<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<h1>${users_page.firstName} ${users_page.lastName}</h1>

<#if user??>
    <#if user.id != users_page.id>
        <button><a href="/messages/${users_page.id}">Чат</a></button>
        <#if !isFollowing>
            <button id="follow_button">follow</button>
        <#else>
            <a>Подписаны</a>
        </#if>
    </#if>
</#if>

<#list posts_for_profile as post>
    <h1>${post.authorFirstName}</h1>
    <h3>${post.date}</h3>
    <h2 class="text_post">${post.text}</h2>
    <#if post.pathToFile??>
        <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
    </#if>
</#list>
</body>
</html>