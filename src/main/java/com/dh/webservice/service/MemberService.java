package com.dh.webservice.service;

import com.dh.webservice.domain.Member;

public interface MemberService  {
    public Member findUserByUserEmail(String email);
    public void saveMember(Member member);
}
