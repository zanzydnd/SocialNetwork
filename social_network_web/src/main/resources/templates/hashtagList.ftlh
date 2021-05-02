<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        .talk_input_container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: auto;
        }

        html {
            height: 100%;
            margin: 0;
            padding: 0;
        }

        body {
            height: 100%;
            margin: 0;
            padding: 0;
            background-color: papayawhip;
            display: flex;
            flex-direction: column;
            align-items: center;
            align-content: center;
        }

        .post_container {
            align-items: center;
            display: flex;
            flex-direction: column;
            padding-top: 20px;
        }

        .post_item {
            background-color: #90D1C4;
            margin-bottom: 85px;
            width: 100%;
            font-size: 20px;
            max-width: 790px;
            border-radius: 3px;
        }

        .comments {

        }</style>
</head>
<body>
<div class="post_container">
    <#list hashtags as hashtag>
        <#list hashtag.posts as post>
            <div class="post_item">
                <h1>${post.getAuthorFirstName()}</h1>
                <h3>${post.date}</h3>
                <h2 class="text_post">${post.text}</h2>
                <#if post.pathToFile??>
                    <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
                </#if>
            </div>
        </#list>
    </#list>
</div>
</body>
</html>