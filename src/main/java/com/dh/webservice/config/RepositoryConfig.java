package com.dh.webservice.config;

import com.dh.webservice.domain.Goods;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
//
//그게 spring-boot-starter-data-rest기본적으로 작동하는 방법이기 때문입니다
// Spring boot -> Spring Data Rest를 사용하는 동안
// @Id가있는 엔티티 속성이 더 이상 JSON에 파싱되지않음
//
//동작 방식을 사용자 정의하려면 RepositoryRestConfigurerAdapter
// 특정 클래스의 ID를 표시 하도록 확장 할 수 있습니다.

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Goods.class);
    }
}