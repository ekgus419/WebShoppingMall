/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.service;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.Role;
import com.dh.webservice.domain.User;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.repository.RoleRepository;
import com.dh.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @title UserService를 구현한 파일
 * @author cdh
 * @FileName : UserServiceImpl
 *
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private GoodsRepository goodsRepository;


    @Override
    public Page<Goods> getfindAll(Integer pageNo, Integer pageSize) {
        PageRequest pr = new PageRequest(pageNo, pageSize,
                new Sort(
                        new Order(Direction.DESC,"goodsNum")
                )
        );

        return goodsRepository.findAll(pr);
    }

//    @Override
//    public Page<Board> getfindAll(Integer pageNo, Integer pageSize) {
//        PageRequest pr = new PageRequest(pageNo, pageSize,
//                new Sort(
//                        new Order(Direction.DESC,"groupNo"),
//                        new Order(Direction.ASC,"groupSeq"),
//                        new Order(Direction.ASC,"depth")
//                )
//        );
//
//        return boardRepository.findAll(pr);
//    }

}
