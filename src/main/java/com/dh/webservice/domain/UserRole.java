//package com.dh.webservice.domain;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Data
//@Entity(name = "USER_ROLE")  // pk 값
//public class UserRole {
//
//    @Id
//    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER_ID"))
//    private User user;
//
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name = "FK_USER_ROLE_ROLE_ID"))
//    private Role role;
//
//
//}