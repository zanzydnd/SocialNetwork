<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA073ajNFNsCVAs0oqzkdhRlx8KgAultYI&callback=initMap&libraries=&v=weekly"
            async
    ></script>
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
</head>
<body>
<#if user??>
    <h1>${user.firstName}</h1>
    <form class="talk_input_container" id="post-create">
        <textarea name="text" id="text" required>Talk</textarea>
        <input hidden name="authorId" id="authorId" value="${user.id}">
        <input type="file" name="pathToFile" id="pathToFile">
        <a id="error-big-data"></a>
        <input id="address" name="address" type="text" placeholder="Введите адресс">
        <button class="submit_button" id="button" name="button">
            Talk
        </button>
    </form>
</#if>


<div class="post_container">
    <div id="new_post">
    </div>
    <#list posts as post>
        <div class="post_item">
            <h1>
                <a href="/profile/${post.authorId}">${post.getAuthorFirstName()}</a>
            </h1>
            <div class="post_text">
                ${post.text}
            </div>
            <#if post.pathToFile??>
                <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
            </#if>
            <button id="like${post.id}" class="like_button" value="${post.id}">like
                <a id="numLikes${post.id}"> ${post.numberOfLikes}</a>
            </button>
            <button id="repost${post.id}" class="repost_button" value="${post.id}">
                <a id="numReposts${post.id}">Репост ${post.numberOfReposts}</a>
            </button>
            <#if post.address??>
                <div class="maps">
                    <input type="hidden" class="params" value="${post.address}">
                    <input type="hidden" class="mapPostId" value="${post.id}">
                    <div id="map${post.id}" style="width: 600px; height: 200px">

                    </div>
                </div>
            </#if>
            <form id="form${post.id}">
                <input type="text" id="comment${post.id}" name="text">
                <input type="hidden" value="${post.id}" name="postId">
                <input type="submit" name="submit" class="btn" value="отправить">
            </form>

            <div class="comments">
                <h2>Comments</h2>
                <div id="comment-append${post.id}">
                </div>
                <#list post.getComments() as comment>
                    <h4>
                        <a href="/profile/${comment.userId}">${comment.userFirstName}</a>
                    </h4>
                    <h5>
                        ${comment.text}
                    </h5>
                </#list>
            </div>
        </div>
    </#list>
</div>
<script>
    function ajaxSubmit() {
        var form = $('#post-create')[0];
        var data = new FormData(form);

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "posts",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function (ret_data, textStatus, xhr) {
                $("#error-big-data").text("");
                $("#text").val("");
                $("#pathToFile").val("");
                var appendable = '<div class="post_item">' +
                    "<h1>" + ret_data['authorFirstName'] + "</h1>\n" +
                    '  <div class="post_text"> <a>' + ret_data['text'] + "</a> </div>\n";

                if (ret_data['pathToFile'] != null) {
                    appendable += "    <img src=\"/uploads/" + ret_data['id'] + "/" + ret_data['pathToFile'] + "\" alt=\"/\">\n";
                }

                $("#new_post").append(appendable +
                    "    <button id=\"like" + ret_data['id'] + "\" class=\"like_button\" value=\"" + ret_data['id'] + "\">like\n" +
                    "        <a id=\"numLikes" + ret_data['id'] + "\">0</a>\n" +
                    "    </button>\n" +
                    "    <form id=\"form" + ret_data['id'] + "\">\n" +
                    "        <input type=\"text\" id=\"comment" + ret_data['id'] + "\" name=\"text\">\n" +
                    "        <input type=\"hidden\" value=\"\" name=\"userId\">\n" +
                    "        <input type=\"hidden\" value=\"" + ret_data['id'] + "\" name=\"postId\">\n" +
                    "        <input type=\"submit\" name=\"submit\" class=\"btn\" value=\"отправить\">\n" +
                    "    </form>\n" + "</div>")
            },
            error: function () {
                $("#error-big-data").text("Большой размер файла");
            }
        });
    }

    function ajaxLike(value) {
        var user_id = ${user.id};
        var post_id = value;
        var data = JSON.stringify({"userId": ${user.id}, "postId": post_id})
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/likes",
            data: data,
            success: function (ret_data, textStatus, xhr) {
                $("#numLikes" + post_id).text(ret_data);
            }
        });
    }


    function ajaxRepost(value) {
        var post_id = value;
        var data = JSON.stringify({"userId": ${user.id}, "postId": post_id})
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/repost",
            data: data,
            success: function (ret_data, textStatus, xhr) {
                $("#numReposts" + post_id).text("Репост " + ret_data);
            }
        });
    }


    $(function () {
        $(document).ready(function () {
            const blockText = $('.post_text');
            blockText.each(function (index) {
                const patternA = new RegExp("#[\\w-]*[a-zA-Z0-9][\\w-]*\\b", "gi");
                blockText[index].innerHTML = blockText[index].textContent.replace(patternA, function (e) {
                    let a_tag = document.createElement("a");
                    a_tag.attributes.href = '#';
                    a_tag.textContent = e;
                    return `<a href='/hashtag/` + e.slice(1) + `'>` + e + `</a>`;
                });
            });
            $('#button').on("click", function (event) {
                event.preventDefault();
                ajaxSubmit();
            });
            $('.like_button').on('click', function (event) {
                event.preventDefault();
                ajaxLike($(this).val());
            });
            $('.repost_button').on('click', function (event) {
                event.preventDefault();
                ajaxRepost($(this).val());
            });
            $('form').submit(function (event) {
                event.preventDefault();
                var formData = new FormData(this);
                var post_id = formData.get("postId");
                var user_id = formData.get("userId");
                var text = formData.get("text");
                var data = JSON.stringify({"text": text, "postId": post_id});
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/comments",
                    data: data,
                    success: function (data, textStatus, xhr) {
                        var postId = data['postId'];
                        var userId = data['userId'];
                        $("#comment" + postId).val('');
                        var text = data['text'];
                        console.log(data);
                        $("#comment-append" + postId).append('<h4>\n' +
                            '                ' + '<a href="/profile/' + userId + '">' + data['userFirstName'] + '</a>'
                             + '\n' +
                            '            </h4>\n' +
                            '            <h5>\n' +
                            '                ' + data['text'] + '\n' +
                            '            </h5>');
                    }
                });
            });
        });
    });
</script>

</body>
</html>