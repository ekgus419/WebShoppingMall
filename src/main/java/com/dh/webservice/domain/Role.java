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

import javax.persistence.*;

/**
 * @title ROLE Entity를 정의한다.
 * @author cdh
 * @FileName Role
 *
 */
@Entity(name = "ROLE")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"role_name"}, name="ROLE_UNIQUE_NAME"))
public class Role extends WebBaseTimeConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_idx")
    private int roleIdx;
    @Column(name = "role_name")
    private String roleName;
}