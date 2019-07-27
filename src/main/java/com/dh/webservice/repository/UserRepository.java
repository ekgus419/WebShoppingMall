/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.repository;

import com.dh.webservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @title User Entity Query 설정 파일
 * @author cdh
 * @FileName UserRepository
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String email);
}