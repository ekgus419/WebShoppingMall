/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.config;

import com.dh.webservice.domain.Goods;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * @title RepositoryConfig를 정의한다.
 * @author cdh
 * @FileName GOODS
 * @version https://stackoverflow.com/questions/24839760/spring-boot-responsebody-doesnt-serialize-entity-id
 *
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    /**
     * configureRepositoryRestConfiguration
     * @param config
     * @return @Id가 있는 Goods Entity를 반환한다.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Goods.class);
    }
}