function commentPost(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    var accessId = $("#access_id").val();

    $.ajax({
        type:"POST",
        url:"/comment",
        data:JSON.stringify({
            "parentId": questionId,
            "type": 1,
            "content": content,
            "accessId": accessId
        }),
        contentType: "application/json",
        datatype: "json",
        success: function(response){

            if (response.code == 200){
                $("#coment_section").hide();
            }else {
                if (response.code == 2006) {
                    var isAccept = confirm(response.message);
                    if (isAccept == true){
                        window.open("https://github.com/login/oauth/authorize?client_id=09f31c8c709c05e5d876&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true)
                    }
                }else {
                    alert(response.message);
                }
            }

        }
    });
}

function over(){
    var div = document.getElementById('warp');//获取大div对象
    var text = document.getElementById('text');//获取文字div对象
    text.style.display="block";
}//鼠标进入显示文字
function out(){
    var div = document.getElementById('warp');
    var text = document.getElementById('text');
    text.style.display="none";
}//鼠标退出不显示文字

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    console.log(value);
    console.log(previous);
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
            console.log($("#tag").val(previous + ',' + value))
        } else {
            $("#tag").val(value);
        }
    }
}