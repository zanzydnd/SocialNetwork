<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<#if user??>
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
</#if>

<div id="new_post">
</div>

<#list posts as post>
    <h1>${post.getAuthorFirstName()}</h1>
    <h3>${post.date}</h3>
    <h2 class="text_post">${post.text}</h2>
    <#if post.pathToFile??>
        <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
    </#if>
    <button id="like${post.id}" class="like_button" value="${post.id}">like
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
        <#list post.getComments() as comment>
            <h4>
                ${comment.userFirstName}
            </h4>
            <h5>
                ${comment.text}
            </h5>
        </#list>
    </div>
</#list>

<script>
    /*        function getBase64Image(imgElement){
                var canvas = document.createElement("canvas");
                canvas.width = imgElement.clientWidth;
                canvas.height = imgElement.clientHeight;
                var ctx = canvas.getContext("2d");
                ctx.drawImage(imgElement,0,0);
                var dataUrl = canvas.toDataURL("image/png");
                return dataUrl.replace(/^data:image\/(png|jpg);base64,/,"");
            }*/
    //рабочий вариант в base64.
    /*
            $(function () {
                console.log(1);
                var reader = new FileReader();

                $('#button').on("click",function (event) {
                    event.preventDefault();
                    //var file_data = .prop('files')[0];
                    var file = $('#post-create > input')[0].files[0];
                    reader.readAsDataURL(file);
                    reader.onload = function (event){
                        var text = $('#text').val();
                        var author_id = $('#authorId').val();
                        var data = event.target.result.replace("data:" + file.type + ";base64,",'');

                        console.log(data);
                        console.log(window.location.href + "posts");
                        var dada = {"text": text, "authorId": author_id, "pathToFile": data}
                        console.log(JSON.stringify(dada));
                        $.ajax({
                            type: "POST",
                            url: window.location.href + "posts",
                            data: JSON.stringify(dada),
                            enctype: 'multipart/form-data',
                            processData: false,
                            cache:false,
                            contentType: false,
                            success: function (result) {

                            }
                        });
                    }
                });
            });
    */
    /*
            $(function () {
                $('#button').on("click", function (event) {
                    event.preventDefault();
                    var file = $('#post-create > input')[0].files[0];
                    var formData = new FormData();
                    formData.append('pathToFile', file)
                    var text = $('#text').val();
                    var author_id = $('#authorId').val();
                    var objArr = [];

                    objArr.push({"text": text, "authorId": author_id});
                    formData.append('objArr', JSON.stringify(objArr));

                    console.log(formData);
                    $.ajax({
                        type: "POST",
                        url: window.location.href + "posts",
                        data: formData,
                        processData: false,
                        cache:false,
                        //contentType: "multipart/form-data",
                        success: function (response){
                            console.log("cool");
                        }
                    });
                });

            });*/
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
                console.log(ret_data);
                $("#new_post").append("<h1>" + ret_data['authorFirstName'] + "</h1>\n" +
                    "    <h3>" + ret_data['date'] + "</h3>\n" +
                    "    <a>" + ret_data['text'] + "</a>\n" +
                    "    <img src=\"/uploads/" + ret_data['id'] + "/" + ret_data['pathToFile'] + "\" alt=\"/\">\n" +
                    "    <button id=\"like" + ret_data['id'] + "\" class=\"like_button\" value=\"" + ret_data['id'] + "\">like\n" +
                    "        <a id=\"numLikes" + ret_data['id'] + "\">0</a>\n" +
                    "    </button>\n" +
                    "    <form id=\"form" + ret_data['id'] + "\">\n" +
                    "        <input type=\"text\" id=\"comment" + ret_data['id'] + "\" name=\"text\">\n" +
                    "        <input type=\"hidden\" value=\"\" name=\"userId\">\n" +
                    "        <input type=\"hidden\" value=\"" + ret_data['id'] + "\" name=\"postId\">\n" +
                    "        <input type=\"submit\" name=\"submit\" class=\"btn\" value=\"отправить\">\n" +
                    "    </form>\n")
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
            const blockText = $('.text_post');
            blockText.each(function (index) {
                const patternA = new RegExp("#[\\w-]*[a-zA-Z0-9][\\w-]*\\b", "gi");
                blockText[index].innerHTML = blockText[index].textContent.replace(patternA, function (e) {
                    let a_tag = document.createElement("a");
                    a_tag.attributes.href = '#';
                    a_tag.textContent = e;
                    return `<a href='/hashtag/` + e.slice(1) +`'>` + e + `</a>`;
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
                            '                ' + data['userFirstName'] + '\n' +
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