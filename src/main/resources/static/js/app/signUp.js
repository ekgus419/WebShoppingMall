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
            url: '/user/signUp',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('가입되었습니다.');
            location.href("/login");
        }).fail(function (error) {
            alert("error" +error);
            console.log(error);
        });
    }

};

main.init();