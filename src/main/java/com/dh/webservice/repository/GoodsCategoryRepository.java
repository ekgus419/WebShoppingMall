/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.repository;

import com.dh.webservice.domain.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @title GoodsCategory Entity Query 설정 파일
 * @author cdh
 * @FileName GoodsCategoryRepository
 *
 */
@Repository
public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {

}