var main = {
    init: function () {
        var _this = this;
        $('#btn_signup').on('click', function () {
            _this.save();
        });
    },
    save: function () {
        var data = {
            userName: $('#user_name').val(),
            userPwd: $('#user_pwd').val(),
            userEmail: $('#user_email').val(),
            // userAddr1: $('#user_addr1').val(),
            // userAddr2: $('#user_addr2').val(),
            // userAddr3: $('#user_addr3').val()
        };
        $.ajax({
            type: 'POST',
            url: '/user/signup',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('가입되었습니다.');
            location.href("/login");
        }).fail(function (error) {
            alert("error" +error);
        });
    }

};

main.init();

$('#update_Btn').on('click', function () {
    // var data = {
    //     bNo: $("#bNo").val(),
    //     writer: $("#writer").val(),
    //     title: $("#title").val(),
    //     content: $("#content").val(),
    // };
    // $.ajax({
    //     type: "PUT",
    //     url: "/board/update/" + data.bNo,
    //     dataType: "json",
    //     contentType: "application/json; charset=utf-8",
    //     data: JSON.stringify(data)
    // }).done(function (result) {
    //     console.log(result);
    //     if(result.bNo > 0){
    //         alert("수정되었습니다.");
    //         location.href= "/board/detail/" + result.bNo;
    //     }else{
    //         alert("자신이 쓴 글만 수정 할 수 있습니다.");
    //         history.go(0);
    //     }
    // }).fail(function (jqXHR, textStatus, errorThrown) {
    //     alert("관리자에게 문의해주세요.");
    //     console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
    // });
});

$('#delete_Btn').on('click', function () {
    alert('삭제');
});