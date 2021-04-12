<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
                url: window.location.href + "api/posts",
                data: data,

                processData: false,
                contentType: false,
                cache: false,
                timeout: 1000000,
                success: function (succes) {
                    console.log("nice");
                }
            });
        }

        function ajaxLike(value) {
            var user_id = ${user.id};
            var post_id = value;
            var data = JSON.stringify({"id": ${user.id}})
            $.ajax({
                type: "POST",
                contentType: "application/json ",
                url: window.location.href + "api/likes/" + post_id,
                data: data,
                success: function (ret_data, textStatus, xhr) {
                    console.log(ret_data)
                    console.log("#numLikes" + post_id);
                    $("#numLikes" + post_id).text(ret_data);
                }
            });
        }


        $(function () {
            $('#button').on("click", function (event) {
                event.preventDefault();
                ajaxSubmit();
            });
            $('.like_button').on('click', function (event) {
                event.preventDefault();
                ajaxLike($(this).val());
            });
            $('form').submit(function (event) {
                event.preventDefault();
                var formData = new FormData(this);
                var post_id = formData.get("postId");
                var user_id = formData.get("userId");
                var text = formData.get("text");
                var data = JSON.stringify({"userId": user_id, "text": text});
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "api/posts/" + post_id + "/comments",
                    data: data,
                    success: function (data, textStatus, xhr) {
                        var postId = data['postId'];
                        var userId = data['userId'];
                        $("#comment" + postId).val('');
                        var text = data['text'];
                        $("#vstavka" + postId).append(userId, text);
                    }
                });
            });
        });
    </script>
</head>
<body>
<#if user??>
    <h1>${user.username}</h1>
    <form class="talk_input_container" id="post-create">
        <textarea name="text" id="text" required>Talk</textarea>
        <input hidden name="authorId" id="authorId" value="${user.id}">
        <input type="file" name="pathToFile" id="pathToFile">
        <button class="submit_button" id="button" name="button">
            Talk
        </button>
    </form>
</#if>

<#list posts as post>
    <h1>${post.authorUsername}</h1>
    <h3>${post.date}</h3>
    <a>${post.text}</a>
    <img src="/uploads/${post.id}/${post.pathToFile}" alt="/">
    <button id="like${post.id}" class="like_button" value="${post.id}">like
        <a id="numLikes${post.id}"> ${post.numberOfLikes}</a>
    </button>
    <form id="form${post.id}">
        <input type="text" id="comment${post.id}" name="text">
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${post.id}" name="postId">
        <input type="submit" name="submit" class="btn" value="отправить">
    </form>
    <div>
        <h2>Comments</h2>
        <#list post.getComments() as comment>
            <h4>
                ${comment.username}
            </h4>
            <h5>
                ${comment.text}
            </h5>
        </#list>
    </div>
    <div id="vstavka${post.id}">
    </div>
</#list>
</body>