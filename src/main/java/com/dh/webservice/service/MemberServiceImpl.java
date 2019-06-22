package com.dh.webservice.service;

import com.dh.webservice.domain.Member;
import com.dh.webservice.domain.Role;
import com.dh.webservice.repository.MemberRepository;
import com.dh.webservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Member findUserByUserEmail(String email) {
        return memberRepository.findByUserEmail(email);
    }

    // 회원 가입시에는 일반 계정만 가능, 관리자는 디비에 삽입.
    @Override
    public void saveMember(Member member) {
        member.setUserPwd(bCryptPasswordEncoder.encode(member.getUserPwd()));
        member.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        member.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        memberRepository.save(member);
    }

}
