package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_addr1")
    private String userAddr1;

    @Column(name = "user_addr2")
    private String userAddr2;

    @Column(name = "user_addr3")
    private String userAddr3;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "active")
    private int active;

//    @Column(name = "userVerify")
//    private String userVerify;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
