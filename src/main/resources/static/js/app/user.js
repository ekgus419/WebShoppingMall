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
            // $('#btn_signup').on('click', function () {
            //     _this.save();
            // });

            goods.render();
            return this;
        },
        getListData: function (offset) {
            $.ajax({
                type: "GET",
                url: "/listData?offset=" + offset,
            }).done(function (data) {
                var content="";
                for(var i=0; i<data.size; i++){
                    if(!data.content[i]){
                        alert("가져올 데이터가 없습니다.");
                        return false;
                    }else{
                        var aHref = "/view?goodsNum=" +data.content[i].goodsNum;
                        var imgSrc = "/uploads/img/" + data.content[i].goodsImg;
                        console.dir(imgSrc);
                        content +=
                            "<tr class='tr-items' data-rowIndex='" + i + "'>"
                            + "<td>"
                                + "<a href='" + aHref + "'>"
                                     + "<img src='" + imgSrc + "'  width='200' height='200'>"
                                + "</a>"
                                + "<br />"
                                + "<span>" + data.content[i].goodsName + "</span>"
                                + "</td>"
                            + "</tr>";
                    }

                }
                $("#btn_more").parent().remove();
                content += "<tr id='btn_add'><td colspan='3'> <button type=\"button\" id=\"btn_more\" class=\"btn btn-primary pull-right \">더보기</button></td></tr>";
                $(content).appendTo("#table");
                goods.render();
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        // save: function () {
        //     var data = {
        //         userName: $('#user_name').val(),
        //         userPwd: $('#user_pwd').val(),
        //         user_phone: $('#user_phone').val(),
        //         userEmail: $('#user_email').val(),
        //         userAddr1: $('#user_addr1').val(),
        //         userAddr2: $('#user_addr2').val(),
        //         userAddr3: $('#user_addr3').val()
        //     };
        //
        //     $.ajax({
        //         type: 'POST',
        //         url: '/user/signUp',
        //         dataType: 'json',
        //         contentType: 'application/json; charset=utf-8',
        //         data: JSON.stringify(data)
        //     }).done(function () {
        //         alert('가입되었습니다.');
        //         location.href("/login");
        //     }).fail(function (error) {
        //         alert("error" +error);
        //         console.log(error);
        //     });
        // },
        render: function () {
            // 상품 3개씩
            $(".tr-items").each(function() {
                var rowIndex = $(this).attr("data-rowIndex");
                if(rowIndex % 3 != 0) {
                    var itemsHtml = $(this).html();
                    var prev = $(this).prev();
                    if(prev.length < 3) {
                        prev.append(itemsHtml);
                    } else {
                        var next = $(this).next();
                        if(next) {
                            next.append(itemsHtml);
                        }
                    }
                    $(this).remove();
                }
            });
        }
    };

    goods.init();



});


// 더보기 버튼 누를시 ver1
// $("#btn_more").on("click", function (e) {
//     console.log(e);
//     count = count + 5;
//     alert(count);
//     _this.getListData(count);
// });