$(document).ready(function(text, reviver){
    // todo admin , user 소스 구분, 파일 새로만들것
    // 글쓰기
    var goods = {
        init: function () {
            var _this = this;
            var path = $(location).attr('pathname');
            // 관리자 수정페이지에서 수정 버튼 누를시
            $("#btn_update").on("click", function () {
                _this.getUpdate();
            });

            // 관리자 상세페이지에서 삭제 버튼 누를시
            $("#btn_delete").on("click", function () {
                _this.getDelete();
            });

            if(path === "/admin/goods/list"){
                // 상품 목록 페이징 처리
                $('#pagenation').bootpag({
                    total: $("#totalPages").val(), // total pages
                    page: $("#currentPage").val(), // default page
                    maxVisible: 10, // visible pagination
                    leaps: false,
                    firstLastUse: true,
                    next: '<img src="/images/paging/next_1.gif" alt="뒤로버튼" />',
                    prev: '<img src="/images/paging/prev_2.gif" alt="맨앞으로버튼" />',
                    first: '<img src="/images/paging/prev_1.gif" alt="맨앞으로버튼" />',
                    last: '<img src="/images/paging/next_2.gif" alt="맨뒤로버튼" />'
                }).unbind("page").bind("page").on("page", function (event, pageNo) {
                    window.location.replace("/admin/goods/list?pageNo=" + pageNo + "&pageSize=10");
                });
            }

            return this;
        },
        getUpdate: function () {
            var goodsNum = $("#goodsNum").val();
            // var data = {
            //     goodsNum: $("#goodsNum").val(),
            //     goodsName: $("#goodsName").val(),
            //     goodsPrice: $("#goodsPrice").val(),
            //     goodsStock: $("#goodsStock").val(),
            //     goodsDescription: $("#goodsDescription").val(),
            //     files : $("#goodsImg").val() ? $("#goodsImg").val() : ""
            // };
            // // var data = $("#updateForm").serialize();
    ;
            // var data = new FormData();
            // data.append("files",$("#goodsImg")[0]);
            var formData = new FormData();
            formData.append("files",$("input[name=goodsImg]")[0].files[0]);

            console.log(formData);


            return false;
            $.ajax({
                type: "POST",
                url: "/admin/goods/update/" + goodsNum,
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


