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