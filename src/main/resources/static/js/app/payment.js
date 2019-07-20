$(document).ready(function(text, reviver){
    // 결제
    var payment = {
        init: function () {
            var _this = this;
            // main 상세페이지에서 구매 버튼 누를시
            $("#btn_buy").on("click", function () {
                _this.getBuy();
            });
            return this;
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
                    if(data.result === true){
                        payment.getPaymentMoule();
                    }else{
                        alert(data.message);
                    }
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    alert("관리자에게 문의해주세요.");
                    console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
                });
            }
        },
        getPaymentMoule: function () {
            var IMP = window.IMP; // 생략가능
            IMP.init('imp36331565');
            IMP.request_pay({
                pg: 'inicis',
                pay_method: 'card',
                merchant_uid: 'merchant_' ,
                name: '주문명:결제테스트',
                amount: 1000,
                buyer_email: 'iamport@siot.do',
                buyer_name: '구매자이름',
                buyer_tel: '010-1234-5678',
                buyer_addr: '서울특별시 강남구 삼성동',
                buyer_postcode: '123-456',
                m_redirect_url: 'https://www.yourdomain.com/payments/complete'
            }, function (rsp) {
                console.log(rsp);
                if (rsp.success) {
                    var msg = '결제가 완료되었습니다.';
                    msg += '고유ID : ' + rsp.imp_uid;
                    msg += '상점 거래ID : ' + rsp.merchant_uid;
                    msg += '결제 금액 : ' + rsp.paid_amount;
                    msg += '카드 승인번호 : ' + rsp.apply_num;
                } else {
                    // todo 결제에 실패했을경우 insert한 데이터 삭제처리해야함. -> flag 필드 추가 y,n
                    var msg = '결제에 실패하였습니다.';
                    msg += "(" + rsp.error_msg + ".)";
                }
                alert(msg);
            });
        },

    };

    payment.init();

});
