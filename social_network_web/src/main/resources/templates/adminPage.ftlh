<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auth</title>
    <link rel="stylesheet" href="#">
</head>
<body>
    <h1>Админка</h1>

    <#list userList as single>
        <div>${single.email} <a href="/ban/${single.id}">ban</a></div>
    </#list>
</body>
</html>