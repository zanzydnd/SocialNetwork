<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<#--<#if user??>
    <h1>${user.firstName}</h1>
    <form class="talk_input_container" id="post-create">
        <textarea name="text" id="text" required>Talk</textarea>
        <input hidden name="authorId" id="authorId" value="${user.id}">
        <input type="file" name="pathToFile" id="pathToFile">
        <a id="error-big-data"></a>
        <button class="submit_button" id="button" name="button">
            Talk
        </button>
    </form>
</#if>-->

<div id="new_post">
</div>

<#list hashtags as hashtag>
    <#list hashtag.posts as post>
        <h1>${post.getAuthorFirstName()}</h1>
        <h3>${post.date}</h3>
        <h2 class="text_post">${post.text}</h2>
        <#if post.pathToFile??>
            <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
        </#if>
    <#--<button id="like${post.id}" class="like_button" value="${post.id}">like
        <a id="numLikes${post.id}"> ${post.numberOfLikes}</a>
    </button>
    <button id="repost${post.id}" class="repost_button" value="${post.id}">
        <a id="numReposts${post.id}">Репост ${post.numberOfReposts}</a>
    </button>
    <form id="form${post.id}">
        <input type="text" id="comment${post.id}" name="text">
        <input type="hidden" value="${post.id}" name="postId">
        <input type="submit" name="submit" class="btn" value="отправить">
    </form>
        <div>
            <h2>Comments</h2>
            <div id="comment-append${post.id}">
            </div>
            <#list post.comments as comment>
                <h4>
                    ${comment.userFirstName}
                </h4>
                <h5>
                    ${comment.text}
                </h5>
            </#list>
        </div>-->
    </#list>
</#list>
</body>
</html>