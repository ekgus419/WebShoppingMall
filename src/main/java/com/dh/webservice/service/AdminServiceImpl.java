/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.service;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.repository.GoodsRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;

/**
 * @title UserService를 구현한 파일
 * @author cdh
 * @FileName : UserServiceImpl
 *
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public String fileUpload(MultipartFile file) throws Exception {

        File destinationFile;
        String destinationFileName;
        String fileUrl = "C:\\uploads\\img\\";
        String fileName = file.getOriginalFilename();
        String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
            destinationFile = new File(fileUrl+ destinationFileName);
        } while (destinationFile.exists());{
            destinationFile.getParentFile().mkdirs();
            file.transferTo(destinationFile);
        }

        return destinationFileName;
    }

}
