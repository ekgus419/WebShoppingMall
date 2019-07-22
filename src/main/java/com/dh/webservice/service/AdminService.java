/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.service;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.User;
import org.springframework.data.domain.Page;

/**
 * @title Repository에 설정되어있는 기본 Query를 사용하지 않고 재정의하여 사용한다.
 * @author cdh
 * @FileName : UserService
 *
 */
public interface AdminService {
    // 리스트 보기
    public Page<Goods> getfindAll(Integer pageNo, Integer pageSize);

}

