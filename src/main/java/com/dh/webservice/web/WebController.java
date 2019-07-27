/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.*;
import com.dh.webservice.repository.GoodsCategoryRepository;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.service.UserService;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @title Web 컨트롤러 파일
 * @author cdh
 * @FileName : WebController
 *
 */
@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    /**
     * main 페이지
     * @return main page view
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/main";
    }

    /**
     * main 페이지
     * @return main page view
     */
    @GetMapping("/main")
    public ModelAndView main(@PageableDefault(sort = { "goodsNum" }, direction = Direction.DESC, size = 6) Pageable pageable ) {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserEmail(auth.getName());

        modelAndView.addObject("user", user);
        modelAndView.setViewName("/main");

        Page<Goods> list = goodsRepository.findAll(pageable);
        modelAndView.addObject("list", list);

        List<GoodsCategory> category = goodsCategoryRepository.findAll();
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    /**
     * Ajax List Data
     * @param page
     * @return list
     */
    @GetMapping("/listData")
    @ResponseBody
    public Page<Goods> listData(@RequestParam("page") int page,
                                @RequestParam("goodsSubCategory") GoodsSubCategory goodsSubCategory){
        int limit = 6;

        Pageable pageable = new PageRequest(page, limit, new Sort(Direction.DESC, "goodsNum"));
        if(goodsSubCategory == null){
            Page<Goods> list = goodsRepository.findAll(pageable);
            return list;
        }else{
            Page<Goods> list = goodsRepository.findGoodsByGoodsSubCategory(goodsSubCategory, pageable );
            if(list.getTotalElements() < 6){
                page = 0;
                list = goodsRepository.findGoodsByGoodsSubCategory(goodsSubCategory, pageable );
            }
            return list;
        }

    }

    /**
     * login 페이지
     * @return login page view
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/signIn");
        return modelAndView;
    }

    /**
     * logout 페이지
     * @return logout page view
     */
    @GetMapping("/signOut")
    public String signOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/user/signIn";
    }
}
