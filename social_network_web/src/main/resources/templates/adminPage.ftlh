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

        .user_container{
            align-items: center;
            display: flex;
            flex-direction: column;
            padding-top: 20px;
        }

        .user_item{
            background-color: #90D1C4;
            margin-bottom: 85px;
            width: 100%;
            font-size: 20px;
            max-width: 790px;
            border-radius: 3px;
        }
    </style>
</head>
<body>
    <h1>Админка</h1>
    <div class="user_container">
    <#list userList as single>
        <div class="user_item">${single.email} <a href="/ban/${single.id}">ban</a></div>
    </#list>
    </div>
</body>
</html>