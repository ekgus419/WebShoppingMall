package com.dh.webservice.repository;

import com.dh.webservice.domain.GoodsCategory;
import com.dh.webservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, Long> {

}