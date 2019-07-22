/**
 * @author cdh
 * @copyright Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 * @since 2019-07-01
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.*;
import com.dh.webservice.repository.GoodsCategoryRepository;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.service.AdminService;
import com.dh.webservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @title Admin 컨트롤러 파일
 * @author cdh
 * @FileName : AdminController
 *
 */
@Controller
@RequestMapping(value = "/admin/*")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    File destinationFile;
    String destinationFileName;
//    String fileUrl = "D:\\WebShoppingMall\\src\\main\\resources\\static\\uploads\\img\\";
    String fileUrl = "C:\\uploads\\img\\";

    /**
     * 인덱스 페이지
     * @return admin main page view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }

    /**
     * 상품 목록
     * @param pageNo
     * @param pageSize
     * @param principal
     * @return 전체 상품 목록 List
     */
//    @GetMapping("/goods/list")
//    public void getGoodsList(Model model) throws Exception {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUserEmail(auth.getName());
//        model.addAttribute("user", user);
//        List<Goods> list = goodsRepository.findAll();
//        model.addAttribute("list", list);
//
//    }
    @GetMapping("/goods/list")
    public ModelAndView list(Optional<Integer> pageNo, Optional<Integer> pageSize, Principal principal){
        String writer = principal.getName();

        int evalPageSize = pageSize.orElse(10);
        int evalPage = (pageNo.orElse(0) < 1) ? 0 : pageNo.get() - 1;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth : " + auth.getName());
        User user = userService.findUserByUserEmail(auth.getName());

        // ajax Data
        Page<Goods> page = adminService.getfindAll(evalPage, evalPageSize);
        ModelAndView model = new ModelAndView("/admin/goods/list");
        // 현재 페이지
        int currentPage = page.getNumber()+1;
        // 전체 페이지
        int totalPages = page.getTotalPages();
        // 전체 데이터수
        long listCount = page.getTotalElements();

        model.addObject("currentPage", currentPage);
        model.addObject("totalPages", totalPages);
        model.addObject("list", page);
        model.addObject("listCount", listCount);
        model.addObject("user", user);

        return model;
    }

    /**
     * 상품 상세 보기
     * @param model
     * @param goodsNum
     * @return 상품 상세 페이지
     */
    @GetMapping("/view")
    public void getGoodsView(Model model, @RequestParam("goodsNum") Long goodsNum) throws Exception {
        Goods goods = goodsRepository.findOne(goodsNum);
        List<GoodsCategory> category = goodsCategoryRepository.findAll();
        model.addAttribute("category", category);
        model.addAttribute("goods", goods);

    }

    /**
     * 상품 수정
     * @param model
     * @param goodsNum
     * @return 상품 수정 뷰 페이지
     */
    @GetMapping("/goods/update/{goodsNum}")
    public String getGoodsModifyGet(Model model, @PathVariable Long goodsNum) throws Exception {
        Goods goods = goodsRepository.findOne(goodsNum);
        model.addAttribute("goods", goods);
        return "/admin/goods/update";
    }

    /**
     * 상품 수정
     * @param goodsNum
     * @param goods
     * @return 수정된 게시글 Entity
     */
    @PutMapping("/goods/update/{goodsNum}")
    @ResponseBody
    public boolean update(@PathVariable Long goodsNum, @RequestBody Goods goods, Principal principal, MultipartFile files) throws Exception {
        String writer = principal.getName();
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            Goods updateGoods = goodsRepository.findOne(goodsNum);
            updateGoods.setGoodsName(goods.getGoodsName());
            updateGoods.setGoodsDescription(goods.getGoodsDescription());
            updateGoods.setGoodsPrice(goods.getGoodsPrice());

            goodsRepository.save(updateGoods);

            return true;
        }else{
            return false;
        }

    }


    /**
     * 상품 삭제
     * @param goodsNum
     * @return boolean
     */
    @DeleteMapping("/goods/delete/{goodsNum}")
    @ResponseBody
    public ReturnResult goodsDelete(@PathVariable Long goodsNum, Principal principal) {
        ReturnResult message = new ReturnResult();
        String writer = principal.getName();
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            goodsRepository.delete(goodsNum);
            message.setResult(true);
            return message;
        }else{
            message.setResult(false);
            return message;
        }
    }

    /**
     * 상품 등록
     * @return 상품 등록 뷰 리턴
     */
    @GetMapping("/goods/register")
    public void getGoodsRegisterGet(Model model) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserEmail(auth.getName());
        List<GoodsCategory> category = goodsCategoryRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("category", JSONArray.fromObject(category));
    }

    /**
     * 상품 등록
     * @param model
     * @param goods
     * @return void
     */
    @PostMapping("/goods/register")
    public String getGoodsRegisterPost(Model model, Goods goods,GoodsCategory goodsCategory,GoodsSubCategory goodsSubCategory, MultipartFile files) throws Exception {

        if(files.isEmpty()){
            goodsRepository.save(goods);
        }else{
            String fileName = files.getOriginalFilename();
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFile = new File(fileUrl+ destinationFileName);
            } while (destinationFile.exists());{
                destinationFile.getParentFile().mkdirs();
                files.transferTo(destinationFile);
            }

            goods.setGoodsImg(destinationFileName);
            goodsRepository.save(goods);
        }
        return "redirect:/admin/index";
    }



}