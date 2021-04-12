<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Talker</title>
    <link rel="stylesheet" href="">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Condensed&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Bitter&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="talker_nav">
        <div class="talker_nav_logo">
            <a href="#" class="Talker_logo">Talker</a>
        </div>
        <div class="talker_nav_you">
            <a class="Talker_you" href="#">Register</a>
            <br>
            <a class="Talker_you" href="#">Login</a>
        </div>
    </div>

    <h1></h1>
    <div class="talks_container">
        <form class="talk_input_container" action="/posts" method="post">
            <textarea name="text" id="text" required>Talk</textarea>
            <button class="submit_button" type="submit">
                Talk
            </button>
        </form>

        <#list posts_page as post>
            <div class="talk">
                <div class="talk_inner_user_name">
                    <a class="talker_username" href="#">${post.userDto.username}</a>
                </div>
                <div class="talk_inner_text">
                    <a class="talk_talk">
                        ${post.text}
                    </a>
                    <button id = "like">like</button>
                </div>
                <div class="talk_inner_answer">
                    <a class="answer" href="#">Answer</a>
                </div>
            </div>
        </#list>
        <div class="talk">
            <div class="talk_inner_user_name">
                <a class="talker_username" href="#">UserName</a>
            </div>
            <div class="talk_inner_text">
                <a class="talk_talk">
                    It's so great to use that service!
                </a>
            </div>
            <div class="talk_inner_answer">
                <a class="answer" href="#">Answer</a>
            </div>
        </div>
    </div>
</div>
<script
        src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&callback=initMap&libraries=&v=weekly"
        async
></script>
</body>
</html>