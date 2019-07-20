$(document).ready(function(text, reviver){
    // todo admin , user 소스 구분, 파일 새로만들것
    // 글쓰기
    var goods = {
        init: function () {
            var _this = this;
            var offset = 5;
            // 더보기 버튼 누를시 ver2
            // -> ajax load 후 이벤트 안걸려서 수정
            $('body').on('click','#btn_more',function(){
                offset = offset + 5;
                _this.getListData(offset);
            });
            // 회원 가입 버튼 누를시
            $('#btn_signup').on('click', function () {
                _this.save();
            });

            return this;
        },
        getListData: function (offset) {
            $.ajax({
                type: "GET",
                url: "/listData?offset=" + offset,
            }).done(function (data) {
                console.log(data);
                var content="";
                for(var i=0; i<data.size; i++){
                    if(data.content[i].goodsNum === undefined){
                        alert("가져올 데이터가 없습니다.");
                        return false;
                    }else{
                        var aHref = "/view?goodsNum=" +data.content[i].goodsNum;
                        var imgSrc = "/uploads/img/" + data.content[i].goodsImg;
                        console.dir(imgSrc);
                        content +=
                            "<tr>"+
                            "<td>"
                            + "<a href=''>"
                            + "<img src=''  width='200' height='200'>"
                            + "</a>"
                            // + $("a").attr("href", aHref)
                            // + $("img").attr("src", imgSrc)
                            +"</td>"
                            +"<td class='text-center' style='vertical-align:middle;'>" + data.content[i].goodsName + "</td>"
                        "</tr>";
                        // $("img").attr("src", imgSrc);
                        // console.log($("img").attr("src", imgSrc));
                    }

                }
                content += "<tr id='btn_add'><td> <button type=\"button\" id=\"btn_more\" class=\"btn btn-primary  \">더보기</button></td></tr>";
                $("#btn_add").remove();
                $(content).appendTo("#table");

            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        save: function () {
            var data = {
                userName: $('#user_name').val(),
                userPwd: $('#user_pwd').val(),
                user_phone: $('#user_phone').val(),
                userEmail: $('#user_email').val(),
                userAddr1: $('#user_addr1').val(),
                userAddr2: $('#user_addr2').val(),
                userAddr3: $('#user_addr3').val()
            };

            console.log(data);

            return false;

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

    goods.init();


    // 회원가입시 form 데이터 검증
    $("#signUpForm").validate({
        focusCleanup: true, //true이면 잘못된 필드에 포커스가 가면 에러를 지움
        focusInvalid:false, //유효성 검사후 포커스를 무효필드에 둠 꺼놓음
        onclick: false, //클릭시 발생됨 꺼놓음
        onfocusout:false, //포커스가 아웃되면 발생됨 꺼놓음
        onkeyup:false, //키보드 키가 올라가면 발생됨 꺼놓음
        rules: {
            user_email : { required: true, email: true},
            user_name: { required: true, minlength: 2},
            user_phone: { required: true, minlength: 11},
            user_addr1: { required: true, minlength: 2},
            user_addr2: { required: true, minlength: 2},
            user_addr3: { required: true, minlength: 2},
            user_pwd: { required: true, minlength: 4 },
            user_repwd: { equalTo: "#user_pwd"}
        },
        messages: {
            user_name: {
                required: "닉네임을 입력하세요.",
                minlength: "닉네임은 2자 이상 입력해주세요."
            },
            user_phone: {
                required: "연락처를 입력하세요.",
                minlength: "연락처는 11자 이상 입력해주세요."
            },
            user_addr1: {
                required: "주소(시)를 입력하세요.",
                minlength: "주소(시)는 2자 이상 입력해주세요."
            },
            user_addr2: {
                required: "주소(구)를 입력하세요.",
                minlength: "주소(구)는 2자 이상 입력해주세요."
            },
            user_addr3: {
                required: "주소(동)를 입력하세요.",
                minlength: "주소(동)는 2자 이상 입력해주세요."
            },
            user_pwd: {
                required: "비밀번호를 입력하세요." ,
                minlength: "비밀번호는 4자 이상 입력해주세요."
            },
            user_repwd: {
                equalTo: "입력하신 비밀번호가 다릅니다."
            },
            user_email: {
                required: "이메일을 입력하세요.",
                user_email: "잘못된 이메일 형식입니다."
            }
        },
        errorPlacement: function (){
            //validator는 기본적으로 validation 실패시 메세지를 우측에 표시하게 되어있다 원치않으면 입력해놓을것 ★안쓰면 에러표시됨★
            console.log("errorPlacement")
        },
        invalidHandler: function(form, validator){
            //입력값이 잘못된 상태에서 submit 할때 호출
            var errors = validator.numberOfInvalids();
            if (errors) {
                alert(validator.errorList[0].message);
                validator.errorList[0].element.focus();
            }
        }
    });


});


// 더보기 버튼 누를시 ver1
// $("#btn_more").on("click", function (e) {
//     console.log(e);
//     count = count + 5;
//     alert(count);
//     _this.getListData(count);
// });