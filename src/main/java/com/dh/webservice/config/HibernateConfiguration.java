package com.dh.webservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HibernateConfiguration {
    // listData for return JSON
    //https://codeday.me/ko/qa/20190503/448013.html
    @Primary
    @Bean(name = "objectMapper")
    public ObjectMapper hibernateAwareObjectMapper(){

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());

        return mapper;
    }
}