package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "GOODS_CATEGORY")
@Data
public class GoodsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_code")
    private Long categoryCode;    // 카테고리 코드

    @Column(name = "category_name")
    private String categoryName; // 카테고리 이름

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="category_code")
    private Collection<Goods> categoryCodeRef;    // 카테고리 참조 코드


}
