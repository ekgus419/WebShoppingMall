package com.dh.webservice.domain;

import com.dh.webservice.repository.GoodsCategoryRepository;
import com.dh.webservice.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class test implements CommandLineRunner{

    @Autowired
    private GoodsRepository goods;

    @Autowired
    private GoodsCategoryRepository goodsCategory;

    public static void main(String[] args) {
        SpringApplication.run(test.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Goods first = new Goods(); // (1)
        first.setCateCode("test");
        goods.save(first); // (3)

        List<Goods> list = goods.findAll(); // (5)

        for( Goods m : list ){
            System.out.println(m.toString());
        } // (6)

        goods.deleteAll(); // (7)
    }
}