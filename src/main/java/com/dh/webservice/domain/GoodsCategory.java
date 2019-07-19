/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @title GOODS_CATEGORY Entity를 정의한다.
 * @author cdh
 * @FileName GoodsCategory
 *
 */
@Entity(name = "GOODS_CATEGORY")
@Data
public class GoodsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_code")
    private Long categoryCode;    // 카테고리 코드

    @Column(name = "category_name")
    private String categoryName; // 카테고리 이름

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_code_ref")
    private List<GoodsSubCategory> goodsSubCategory;    // 하위분류

}
