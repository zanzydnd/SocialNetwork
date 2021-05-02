<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function followUser(followId){
            let data = JSON.stringify({userId : "${user.id}", followId: followId});
            console.log(data);
            $.ajax({
               type : "POST",
               contentType: "application/json",
               url: "/follow",
               data: data,
               success: function (){
                   console.log(followId);
                   $("#follow_button").replaceWith('<button value="' + followId +  '" id="unfollow_button">unfollow</button>');
               }
            });
        }

        function unfollowUser(unfollowId){
            let data = JSON.stringify({userId : "${user.id}", followId: unfollowId});
            console.log(data);
            $.ajax({
                type : "POST",
                contentType: "application/json",
                url: "/unfollow",
                data: data,
                success: function (){
                    $("#unfollow_button").replaceWith('<button value="' + unfollowId +  '" id="follow_button">follow</button>');
                }
            });
        }

        $(function () {
           $("#follow_button").on("click", function (event){
              event.preventDefault();
              followUser($(this).val());
           });
            $("#unfollow_button").on("click", function (event){
                event.preventDefault();
                unfollowUser($(this).val());
            });
        });
    </script>
</head>
<body>
<h1>${users_page.firstName} ${users_page.lastName}</h1>

<#if user??>
    <#if user.id != users_page.id>
        <button><a href="/messages/${users_page.id}">Чат</a></button>
        <#if !isFollowing>
            <button value="${users_page.id}" id="follow_button">follow</button>
        <#else>
            <button value="${users_page.id}" id="unfollow_button">unfollow</button>
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