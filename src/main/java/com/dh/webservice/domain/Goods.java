package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "GOODS")
@Data
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gds_num")
    private Long gdsNum;    // 상품 번호

    @Column(name = "gds_name")
    private String gdsName; // 상품 이름

    @Column(name = "cate_code")
    private String cateCode;    // 분류

    @Column(name = "gds_price")
    private int gdsPrice;   // 상품 가격

    @Column(name = "gds_stock")
    private String gdsStock;    // 상품 수량

    @Column(name = "gds_des")
    private String gdsDes;      // 상품 설명

    @Column(name = "gds_img")
    private String gdsImg;      // 상품 이미지

    @Column(name = "gds_date")
    private String gdsDate;     // 상품 등록일




}
