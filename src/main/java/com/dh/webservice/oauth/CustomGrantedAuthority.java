/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.oauth;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;

/**
 * @title Spring Security 설정 파일
 * @author cdh
 * @FileName CustomGrantedAuthority
 *
 */
@SuppressWarnings("serial")
public class CustomGrantedAuthority implements GrantedAuthority, Serializable {

    private String name;

    public CustomGrantedAuthority(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomGrantedAuthority [name=" + name + "]";
    }
}
