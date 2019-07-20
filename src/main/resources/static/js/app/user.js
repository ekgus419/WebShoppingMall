$(document).ready(function(text, reviver){
    // todo admin , user 소스 구분, 파일 새로만들것
    // 글쓰기
    var goods = {
        init: function () {
            var _this = this;
            var offset = 5;
            // 관리자 수정페이지에서 수정 버튼 누를시
            $("#btn_update").on("click", function () {
                _this.getUpdate();
            });
            // 관리자 상세페이지에서 삭제 버튼 누를시
            $("#btn_delete").on("click", function () {
                _this.getDelete();
            });

            // 더보기 버튼 누를시 ver1
            // $("#btn_more").on("click", function (e) {
            //     console.log(e);
            //     count = count + 5;
            //     alert(count);
            //     _this.getListData(count);
            // });

            // 더보기 버튼 누를시 ver2
            // -> ajax load 후 이벤트 안걸려서 수정
            $('body').on('click','#btn_more',function(){
                offset = offset + 5;
                _this.getListData(offset);
            });

            // main 상세페이지에서 구매 버튼 누를시
            $("#btn_buy").on("click", function () {
                _this.getBuy();
            });
            return this;
        },
        getListData: function (offset) {
            $.ajax({
                type: "GET",
                url: "/listData?offset=" + offset,
                // url: "/listData?offset=" + offset + "&limit=" + limit,
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
                content += "<tr id='btn_add'><td> <button type=\"button\" id=\"btn_more\" class=\"btn btn-primary pull-right \">더보기</button></td></tr>";
                $("#btn_add").remove();
                $(content).appendTo("#table");

            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getUpdate: function () {
            var data = {
                goodsNum: $("#goodsNum").val(),
                goodsName: $("#goodsName").val(),
                goodsPrice: $("#goodsPrice").val(),
                goodsStock: $("#goodsStock").val(),
                goodsDescription: $("#goodsDescription").val(),
                goodsImg : $("#goodsImg").val() ? $("#goodsImg").val() : ""
            };
            $.ajax({
                type: "PUT",
                url: "/admin/goods/update/" + data.goodsNum,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function (result) {
                console.log(result);
                if(result == true){
                    alert("수정되었습니다.");
                    location.href= "/admin/index/" ;
                }else{
                    alert("잠시후 다시 시도해주세요.");
                    history.go(0);
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getDelete: function () {
            var goodsNum =  $("#goodsNum").val();
            $.ajax({
                type: "DELETE",
                url: "/admin/goods/delete/" + goodsNum,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
            }).done(function (data) {
                console.log(data);
                if(data.result === true){
                    alert("삭제되었습니다.");
                    location.href= "/admin/index/" ;
                }else{
                    alert("잠시후 다시 시도해주세요.");
                    history.go(0);
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
        getBuy: function () {
            if(confirm("구매 하시겠습니까?")){
                // 구매 할 경우
                var data = {
                    goodsNum: $("#goodsNum").val(),
                    goodsName: $("#goodsName").val(),
                    goodsPrice: $("#goodsPrice").val(),
                };
                $.ajax({
                    type: "POST",
                    url: "/user/buy",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(data)
                }).done(function (data) {
                    if(data.restult === true){
                        alert(data.message);
                    }else{
                        alert(data.message);
                    }
                    console.log(data);
                    console.log(data);
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    alert("관리자에게 문의해주세요.");
                    console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
                });
            }
        },

    };

    goods.init();

    // 상품 등록 후 이미지 목록에 표시
    $("#goodsImg").change(function(){
        if(this.files && this.files[0]) {
            var reader = new FileReader;
            reader.onload = function(data){
                $(".select_img img").attr("src", data.target.result).width(500);
            }
            reader.readAsDataURL(this.files[0]);
        }
    });


    // // 상품 등록 Page -> 카테고리
    // var jsonData = JSON.parse($("#categoryValue").val());

    $(".category1").change(function(){
        // 상품 등록 Page -> 카테고리
        var jsonData = JSON.parse($("#categoryValue").val());

        var cate2Arr = new Array();
        var cate2Obj = new Object();
        for(var i = 0; i < jsonData.length; i++) {
            for(var j = 0; j < jsonData[i].goodsSubCategory.length; j++) {
                cate2Obj = new Object();  //초기화
                cate2Obj.categoryName = jsonData[i].goodsSubCategory[j].categoryName;
                cate2Obj.categoryCodeRef = jsonData[i].goodsSubCategory[j].categoryCodeRef;
                cate2Obj.categorySubCode = jsonData[i].goodsSubCategory[j].categorySubCode;
                cate2Arr.push(cate2Obj);
            }
        }

        var cate2Select = $("select.category2");

        cate2Select.children().remove();

        $("option:selected", this).each(function(){
            var selectVal = $(this).val();
            cate2Select.append("<option value=''>전체</option>");
            for(var i = 0; i < cate2Arr.length; i++) {
                if(selectVal == cate2Arr[i].categoryCodeRef) {
                    cate2Select.append("<option value='" + cate2Arr[i].categorySubCode + "'>"
                        + cate2Arr[i].categoryName + "</option>");
                }
            }
        });
    }); // end of category1.chang();

});


