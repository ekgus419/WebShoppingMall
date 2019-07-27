/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.repository;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.GoodsSubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @title Goods Entity Query 설정 파일
 * @author cdh
 * @FileName GoodsRepository
 *
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Page<Goods> findGoodsByGoodsSubCategory(GoodsSubCategory goodsSubCategory, Pageable pageable);

}