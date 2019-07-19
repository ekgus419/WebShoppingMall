/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import com.dh.webservice.config.WebBaseTimeConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
/**
 * @title GOODS Entity를 정의한다.
 * @author cdh
 * @FileName GOODS
 *
 */
@Entity(name = "GOODS")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="GOODS")
public class Goods  extends WebBaseTimeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "goods_num")
    private Long goodsNum;    // 상품 번호

    @Column(name = "goods_name")
    private String goodsName; // 상품 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code")
    private GoodsCategory goodsCategory;    // 분류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_sub_code")
    private GoodsSubCategory goodsSubCategory;    // 하위분류

    @Column(name = "goods_price")
    private int goodsPrice;   // 상품 가격

    @Column(name = "goods_stock")
    private String goodsStock;    // 상품 수량

    @Column(name = "goods_description")
    private String goodsDescription;      // 상품 설명

    @Column(name = "goods_img")
    private String goodsImg;      // 상품 이미지

    @Column(name = "goods_Thumb_img")
    private String goodsThumbImg;      // 상품 이미지

}
