/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @title jackson-datatype-hibernate(Hibernate 5)Object Mapper를 정의한다.
 * @author cdh
 * @FileName HibernateConfiguration
 * @version  https://codeday.me/ko/qa/20190503/448013.html
 */
@Configuration
public class HibernateConfiguration {
    /**
     * hibernateAwareObjectMapper
     * @return Hibernate5Module를 반환한다.
     */
    @Primary
    @Bean(name = "objectMapper")
    public ObjectMapper hibernateAwareObjectMapper(){

        ObjectMapper mapper = new ObjectMapper();
        // 직렬화 사용시
//        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.registerModule(new Hibernate5Module());
        return mapper;
    }
}