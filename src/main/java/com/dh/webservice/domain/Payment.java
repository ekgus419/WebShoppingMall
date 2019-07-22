/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @title PAYMENT Entity를 정의한다.
 * @author cdh
 * @FileName PAYMENT
 *
 */
@Entity(name = "PAYMENT")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_num")
    private Long paymentNum;    // 결제 번호

    @NotNull
    @Column(name = "goods_num")
    private String goodsNum; // 상품 번호

    @NotNull
    @Column(name = "goods_name")
    private String goodsName; // 상품 이름

    @NotNull
    @Column(name = "goods_price")
    private String goodsPrice; // 상품 가격

    @NotNull
    @Column(name = "user_email") // 결제자
    private String userEmail;

    @NotNull
    @Column(name = "flag") // 결제 flag 설정
    private String flag;

}
