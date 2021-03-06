/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.domain;

import com.dh.webservice.config.WebBaseTimeConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @title USER Entity를 정의한다.
 * @author cdh
 * @FileName User
 *
 */
@Entity(name = "USER")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends WebBaseTimeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_idx")
    private Long userIdx;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @Column(name = "user_pwd")
    private String userPwd;

    @NotNull
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "user_phone")
    private String userPhone;

    @NotNull
    @Column(name = "user_addr1")
    private String userAddr1;   // 시

    @NotNull
    @Column(name = "user_addr2")
    private String userAddr2;   // 구

    @NotNull
    @Column(name = "user_addr3")
    private String userAddr3;   // 동

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_idx"), inverseJoinColumns = @JoinColumn(name = "role_idx"))
    private Set<Role> roles;

}
