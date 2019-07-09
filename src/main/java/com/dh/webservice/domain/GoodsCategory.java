/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import com.dh.webservice.config.WebBaseTimeConfig;
import lombok.Data;

import javax.persistence.*;

/**
 * @title GOODS_CATEGORY Entity를 정의한다.
 * @author cdh
 * @FileName GoodsCategory
 *
 */
@Entity(name = "GOODS_CATEGORY")
@Data
public class GoodsCategory extends WebBaseTimeConfig {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "goods_category_idx")
//    private Long goodsCategoryIdx;    // 상품 카테고리 idx

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_code")
    private Long categoryCode;    // 카테고리 코드

    @Column(name = "category_code_ref")
    private String categoryCodeRef; // 카테고리 참조 코드

    @Column(name = "category_name")
    private String categoryName; // 카테고리 이름

}
