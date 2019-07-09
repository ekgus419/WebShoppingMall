/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.service;

import com.dh.webservice.domain.Role;
import com.dh.webservice.domain.User;
import com.dh.webservice.oauth.CustomGrantedAuthority;
import com.dh.webservice.oauth.CustomUserDetails;
import com.dh.webservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @title Spring Security Login 메소드 구현
 * @author cdh
 * @FileName : UserDetailsServiceImpl
 *
 */
@Service
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(username);
        if(user != null) {
            // 로그인된 정보 담기
            CustomUserDetails customUserDetails = new CustomUserDetails();
            customUserDetails.setUserName(user.getUserEmail());
            customUserDetails.setPassword(user.getUserPwd());
            // 로그인한 회원의 권한 목록
            Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
            for (Role role : user.getRoles()) {
                roles.add(new CustomGrantedAuthority(role.getRoleName()));
            }
            customUserDetails.setGrantedAuthorities(roles);
            return customUserDetails;
        }
        throw new UsernameNotFoundException(username);
    }

}