<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA073ajNFNsCVAs0oqzkdhRlx8KgAultYI&callback=initMap&libraries=&v=weekly"
            async
    ></script>
    <script>
        function followUser(followId) {
            let data = JSON.stringify({userId: "${user.id}", followId: followId});
            console.log(data);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/follow",
                data: data,
                success: function () {
                    console.log(followId);
                    $("#follow_button").replaceWith('<button value="' + followId + '" id="unfollow_button">unfollow</button>');
                }
            });
        }

        function unfollowUser(unfollowId) {
            let data = JSON.stringify({userId: "${user.id}", followId: unfollowId});
            console.log(data);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/unfollow",
                data: data,
                success: function () {
                    $("#unfollow_button").replaceWith('<button value="' + unfollowId + '" id="follow_button">follow</button>');
                }
            });
        }

        $(function () {
            $("#follow_button").on("click", function (event) {
                event.preventDefault();
                followUser($(this).val());
            });
            $("#unfollow_button").on("click", function (event) {
                event.preventDefault();
                unfollowUser($(this).val());
            });
        });
    </script>
    <script>
        let map;

        function begin() {
            divs = document.getElementsByClassName("maps");
            for (let i = 0; i < divs.length; i++) {
                var params = divs[i].getElementsByClassName("params")[0].value;
                var postId = divs[i].getElementsByClassName("mapPostId")[0].value;
                initMap(params, postId);
            }
        }

        function initMap(params, postid) {
            console.log(postid);
            params = JSON.parse(params)
            params.lng = parseFloat(params.lng);
            params.lat = parseFloat(params.lat);
            console.log(params);
            map = new google.maps.Map(document.getElementById("map" + postid), {
                center: params,
                zoom: 8,
            });
            new google.maps.Marker({
                position: params,
                map: map
            });
        }

        window.onload = begin;
    </script>
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

        }
    </style>
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
<div class="post_container">
    <#list posts_for_profile as post>
        <div class="post_item">
            <h1><a href="/profile/${post.authorId}">${post.authorFirstName} </a></h1>
            <div class="post_text">${post.text}</div>
            <#if post.pathToFile??>
                <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
            </#if>

            <#if post.address??>
                <div class="maps">
                    <input type="hidden" class="params" value="${post.address}">
                    <input type="hidden" class="mapPostId" value="${post.id}">
                    <div id="map${post.id}" style="width: 600px; height: 200px">
                    </div>
                </div>
            </#if>
        </div>
    </#list>
</div>
</body>
</html>