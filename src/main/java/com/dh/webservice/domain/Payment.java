package com.dh.webservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @title GOODS Entity를 정의한다.
 * @author cdh
 * @FileName GOODS
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

    @Column(name = "goods_num")
    private String goodsNum; // 상품 번호

    @Column(name = "goods_name")
    private String goodsName; // 상품 이름

    @Column(name = "goods_price")
    private String goodsPrice; // 상품 가격

    @Column(name = "user_email") // 결제하는 사람
    private String userEmail;

}
