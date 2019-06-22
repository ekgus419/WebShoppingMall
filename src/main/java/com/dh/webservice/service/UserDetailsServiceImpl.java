package com.dh.webservice.service;

import com.dh.webservice.domain.Member;
import com.dh.webservice.domain.MemberRole;
import com.dh.webservice.domain.Role;
import com.dh.webservice.oauth.CustomGrantedAuthority;
import com.dh.webservice.oauth.CustomUserDetails;
import com.dh.webservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername() " + username);

        Member member = memberRepository.findByUserEmail(username);
        MemberRole memberRole = new MemberRole();
        memberRole.getRole();
        if(member != null) {
            System.out.println("loadUserByUsername() 2 " + username);
            // 로그인된 정보 담기
            CustomUserDetails customUserDetails = new CustomUserDetails();
            customUserDetails.setUserName(member.getUserEmail());
            customUserDetails.setPassword(member.getUserPwd());
            // 로그인한 회원의 권한 목록
            Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
            for (Role role : member.getRoles()) {
                roles.add(new CustomGrantedAuthority(role.getRoleName()));
            }
            customUserDetails.setGrantedAuthorities(roles);
            System.out.println("loadUserByUsername : " + customUserDetails.toString());

            return customUserDetails;
        }
        System.out.println("loadUserByUsername()3 " + username);
        throw new UsernameNotFoundException(username);
    }

}