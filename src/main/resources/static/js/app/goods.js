$(document).ready(function(text, reviver){
    // 글쓰기
    var goods = {
        init: function () {
            var _this = this;
            // 수정페이지에서 수정 버튼 누를시
            $("#btn_update").on("click", function () {
                _this.getUpdate();
            });
            // 상세페이지에서 삭제 버튼 누를시
            $("#btn_delete").on("click", function () {
                _this.getDelete();
            });
            return this;
        },
        getUpdate: function () {
            var data = {
                goodsNum: $("#goodsNum").val(),
                goodsName: $("#goodsName").val(),
                goodsPrice: $("#goodsPrice").val(),
                goodsStock: $("#goodsStock").val(),
                goodsDescription: $("#goodsDescription").val(),
                // files: $("#goodsImg").val() ? $("#goodsImg").val() : ""
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
            alert(goodsNum);
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


    // 상품 등록 Page -> 카테고리
    var jsonData = JSON.parse($("#categoryValue").val());

    $(".category1").change(function(){
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

    // handlebars 테스트
    // 유저 목록
    // todo 이미지 그리는 방법.. div, span 어렵.
});


