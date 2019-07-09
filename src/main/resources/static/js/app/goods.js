$(document).ready(function(){
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

});


