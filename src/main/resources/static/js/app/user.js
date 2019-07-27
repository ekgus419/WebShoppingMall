$(document).ready(function(text, reviver){
    var User = {
        init: function () {
            var _this = this;
            var page = 0;
            var goodsSubCategory =  "";

            $('body').on('click','#btn_more',function(){
                page++;
                _this.getListData(page, goodsSubCategory);
            });

            $(".btn-category").on("click", function(e) {
                e.preventDefault();
                $(".tr-items").remove();
                var changeGoodsSubCategory = $(this).attr("data-value");
                if(goodsSubCategory != changeGoodsSubCategory) {
                    goodsSubCategory = changeGoodsSubCategory;
                    page = 0;
                }
                _this.getListData(page, goodsSubCategory);
            });

            User.render();
            return this;
        },
        getListData: function (page, goodsSubCategory) {
            $.ajax({
                type: "GET",
                url: "/listData?page=" + page + "&goodsSubCategory=" + goodsSubCategory ,
            }).done(function (data) {
                var content="";
                var contentLength = data.content.length;
                if(!contentLength) {
                    alert("가져올 데이터가 없습니다.");
                    return false;
                }
                for(var i=0; i<contentLength; i++){
                    var aHref = "/view?goodsNum=" +data.content[i].goodsNum;
                    var imgSrc = "/uploads/img/" + data.content[i].goodsImg;
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
                    $("#btn_more").parent().remove();
                    content += "<tr id='btn_add'><td colspan='3'> <button type=\"button\" id=\"btn_more\" class=\"btn btn-primary pull-right \">더보기</button></td></tr>";
                    $(content).appendTo("#table");
                    User.render();
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
        },
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

    User.init();

});