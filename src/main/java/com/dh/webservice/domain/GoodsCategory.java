package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "GOODS_CATEGORY")
@Data
public class GoodsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cate_code")
    private Long cateCode;    // 카테고리 코드

    @Column(name = "cate_name")
    private String cateName; // 카테고리 이름

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="cate_code")
    private Collection<Goods> cafeCodeRef;    // 카테고리 참조 코드


}
