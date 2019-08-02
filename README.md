# WebShoppingmall

이 프로젝트는 쇼핑몰의 대표적인 기능을 구현해본 프로젝트 입니다.
이번 프로젝트를 통해 사용자 권한에 따라 각 기능을 구현해보았고,
상품 카테고리를 통한 검색 로직, 더보기 버튼을 활용한 페이징 처리,
이미지 파일 업로드 로직, 결제 로직등을 구현해보았습니다.

##  SKILL
- Spring Boot
- Spring DevTools
- Spring data JPA
- Spring Security
- jquery
- BootStrap
- iamport(결제 연동 모듈)
- Handelbars
- Git
- Gradle

## 주요 기능

1. jQuery BootPag Plugin을 활용한 Paging 로직(관리자Page)
2. 더보기 버튼을 활용한 페이징 처리(사용자Page)
2. jQuery Validation Plugin을 활용한 데이터 검증 로직
2. Spring Security를 이용하여 3개의 Table을 연동한 로그인 로직(USER, ROLE, USER_ROLE)
3. Spring Security ROLE을 활용한 관리자 / 일반 회원 구분 로직
	- 로그인시 권한에 따른 default 페이지 설정
	- 사용자 권한에 따른 기능 구분 로직
3. 회원가입 로직
4. LocalDateTime을 활용한 공통 날짜 데이터 정의 로직
5. 프로젝트 기본 로직
- 글 목록
- 글 쓰기
- 글 수정
- 글 삭제
- 글 상세
- 파일 업로드
- 카테고리 검색

## What was difficult?
프로젝트를 구상 할 때 가장 고민이 많았던 점은 결제 관련 로직이였습니다.
실제 결제가 어떻게 이뤄지는지에 대해 깊게 생각해본적이 없어 다소 삽질을 많이 했습니다.
검색을 통해 알게된 자료들을 통해 실무에서는 모듈을 연동해 구현하는 방식이 많다는 점을 알게되었고
사용 해볼 수 있는 모듈을 찾아 구현하는 쪽으로 방향을 잡았습니다.
아임포트라는 결제 모듈을 연동하고, 결제 테이블을 만들어 간략하게 데이터를 삽입 해주는 형식으로 구현 했습니다.

```javascript
// 결제 모듈
$(document).ready(function(text, reviver){

    // 결제 flag 설정
    var flag = "Yes";

    // 결제
    var payment = {
        init: function () {
            var _this = this;
            // main 상세페이지에서 구매 버튼 누를시
            $("#btn_buy").on("click", function () {
                _this.getBuy(flag);
            });
            return this;
        },
        getBuy: function (flag) {
            if(flag === "Yes"){
               if(confirm("구매 하시겠습니까?")){
                   // 구매 할 경우
                   var data = {
                       goodsNum: $("#goodsNum").val(),
                       goodsName: $("#goodsName").val(),
                       goodsPrice: $("#goodsPrice").val(),
                       flag : flag
                   };
               } else {
                   return false;
               }

            } else if(flag === "No"){
                var data = {
                    flag : flag
                }
            } else{
                alert("잠시후 다시 시도해주세요.");
                return false;
            }

            $.ajax({
                type: "POST",
                url: "/user/buy",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data)
            }).done(function (data) {
                if(data.result === true){
                    payment.getPaymentMoule();
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("관리자에게 문의해주세요.");
                console.log(jqXHR, " " + textStatus + " " + errorThrown + " ");
            });
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
                buyer_email: '구매자 이메일',
                buyer_name: '구매자',
                buyer_tel: '구매자 연락처',
                buyer_addr: '서울특별시 강남구 삼성동',
                buyer_postcode: '구매자 우편 번호',
                m_redirect_url: '구매 후 리다이렉트 될 페이지'
            }, function (rsp) {
                if (rsp.success) { // 결제 성공시
                    var msg = '결제가 완료되었습니다.';
                    msg += '고유ID : ' + rsp.imp_uid;
                    msg += '상점 거래ID : ' + rsp.merchant_uid;
                    msg += '결제 금액 : ' + rsp.paid_amount;
                    msg += '카드 승인번호 : ' + rsp.apply_num;
					
				} else { // 결제 실패시
                    flag = "No";
                    payment.getBuy(flag);
                    var msg = rsp.error_msg + ".";
                }
                alert(msg);
            });
        },

    };

    payment.init();

});
```

서버단에서는 넘겨받은 데이터를 테이블에 삽입해주는 방식으로 간단히 구현하였습니다.

```java
@PostMapping("/buy")
    @ResponseBody
    public ReturnResult getGoodsBuy(@RequestBody Payment payment, Principal principal) throws Exception {

        ReturnResult result = new ReturnResult();

        String writer = principal.getName();
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            if(payment.getFlag().equals("Yes")){
                payment.setUserEmail(writer);
                paymentRepository.save(payment);
                result.setResult(true);
            }else{
                Payment updatePayment = paymentRepository.findTopByOrderByPaymentNumDesc();
                updatePayment.setFlag("No");
                paymentRepository.save(updatePayment);
                result.setResult(false);
            }
            return result;
        }
        else{
            result.setResult(false);
            return result;
        }
    }

```

