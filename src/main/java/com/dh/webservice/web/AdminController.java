/**
 * @author cdh
 * @copyright Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 * @since 2019-07-01
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.RetrunMessage;
import com.dh.webservice.domain.User;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

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

    /**
     * 인덱스 페이지
     * @return admin main page view
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
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
     * @param model
     * @return 전체 상품 목록 List
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public void getGoodsList(Model model) throws Exception {
        System.out.println("get goods list(); ");
        List<Goods> list = goodsRepository.findAll();
        System.out.println("goodsList.toStrong () +  " + list.toString());
        model.addAttribute("list", list);

    }

    /**
     * 상품 상세 보기
     * @param model
     * @param goodsNum
     * @return 상품 상세 페이지
     */
    @RequestMapping(value = "/goods/view", method = RequestMethod.GET)
    public void getGoodsView(Model model, @RequestParam("goodsNum") Long goodsNum) throws Exception {
        System.out.println("get goods view(); ");
        Goods goods = goodsRepository.findOne(goodsNum);
        model.addAttribute("goods", goods);

    }

    /**
     * 상품 수정
     * @param model
     * @param goodsNum
     * @return 상품 수정 뷰 페이지
     */
    @RequestMapping(value = "/goods/update/{goodsNum}", method = RequestMethod.GET)
    public String getGoodsModifyGet(Model model, @PathVariable Long goodsNum) throws Exception {
        System.out.println("get goods update(); ");
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
    public boolean update(@PathVariable Long goodsNum, @RequestBody Goods goods, Principal principal) {
        System.out.println("================= goods update get view ======================");
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
    public RetrunMessage goodsDelete(@PathVariable Long goodsNum, Principal principal) {
        System.out.println("================= goods delete() ======================");
        System.out.println("================= principal ======================" + principal.getName());
        RetrunMessage message = new RetrunMessage();
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
    @RequestMapping(value = "/goods/register", method = RequestMethod.GET)
    public String getGoodsRegisterGet() throws Exception {
        return "/admin/goods/register";
    }

    /**
     * 상품 등록
     * @param model
     * @param goods
     * @return void
     */
    @RequestMapping(value = "/goods/register", method = RequestMethod.POST)
    public String getGoodsRegisterPost(Model model, Goods goods) throws Exception {
        System.out.println("/goods/register() ");
        System.out.println(goods.toString());
        goodsRepository.save(goods);
        return "redirect:/admin/index";
    }

    // 상품 등록
//    @RequestMapping(value = "/goods/register", method = RequestMethod.POST)
//    public String postGoodsRegister(Goods goods, MultipartFile file) throws Exception {
//
//        String imgUploadPath = uploadPath + File.separator + "imgUpload";  // 이미지를 업로드할 폴더를 설정 = /uploadPath/imgUpload
//        String ymdPath = UploadFileUtils.calcPath(imgUploadPath);  // 위의 폴더를 기준으로 연월일 폴더를 생성
//        String fileName = null;  // 기본 경로와 별개로 작성되는 경로 + 파일이름
//
//        if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
//            // 파일 인풋박스에 첨부된 파일이 없다면(=첨부된 파일이 이름이 없다면)
//
//            fileName =  UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);
//
//            // gdsImg에 원본 파일 경로 + 파일명 저장
//            goods.setGoodsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
//            // gdsThumbImg에 썸네일 파일 경로 + 썸네일 파일명 저장
////            goods.setGoodsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
//
//        } else {  // 첨부된 파일이 없으면
//            fileName = File.separator + "images" + File.separator + "none.png";
//            // 미리 준비된 none.png파일을 대신 출력함
//
//            goods.setGdddsImg(fileName);
////            goods.setGoodsThumbImg(fileName);
//        }
//
//        adminService.register(vo);
//
//        return "redirect:/admin/index";
//    }


}