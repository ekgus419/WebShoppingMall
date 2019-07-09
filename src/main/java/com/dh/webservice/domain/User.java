/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @title USER Entity를 정의한다.
 * @author cdh
 * @FileName User
 *
 */
@Entity(name = "USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_idx")
    private Long userIdx;

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
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_idx"), inverseJoinColumns = @JoinColumn(name = "role_idx"))
    private Set<Role> roles;

}
