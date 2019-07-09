package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "GOODS")
@Data
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "goods_num")
    private Long goodsNum;    // 상품 번호

    @Column(name = "goods_name")
    private String goodsName; // 상품 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private GoodsCategory goodsCategory;    // 분류

    @Column(name = "goods_price")
    private int goodsPrice;   // 상품 가격

    @Column(name = "goods_stock")
    private String goodsStock;    // 상품 수량

    @Column(name = "goods_description")
    private String goodsDescription;      // 상품 설명

    @Column(name = "goods_img")
    private String goodsImg;      // 상품 이미지

    @Column(name = "goods_date")
    private String goodsDate;     // 상품 등록일




}
