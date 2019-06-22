package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "MEMBER_ROLE")
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "bigint unsigned")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(name = "FK_MEMBER_ROLE_MEMBER_ID"))
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name = "FK_MEMBER_ROLE_ROLE_ID"))
    private Role role;


}