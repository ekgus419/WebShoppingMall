/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @title Repository에 설정되어있는 기본 Query를 사용하지 않고 재정의하여 사용한다.
 * @author cdh
 * @FileName : UserService
 *
 */
public interface AdminService {

    String fileUpload(MultipartFile file) throws Exception;
}

