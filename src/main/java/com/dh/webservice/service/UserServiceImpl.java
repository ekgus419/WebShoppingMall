/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.service;

import com.dh.webservice.domain.User;
import com.dh.webservice.domain.Role;
import com.dh.webservice.repository.UserRepository;
import com.dh.webservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User findUserByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    // 회원 가입시에는 일반 계정만 가능, 관리자는 디비에 삽입.
    @Override
    public void saveUser(User user) {
        user.setUserPwd(bCryptPasswordEncoder.encode(user.getUserPwd()));
        user.setActive(1);
        Role userRole = roleRepository.findByRoleName("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

}
