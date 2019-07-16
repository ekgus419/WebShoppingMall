/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.User;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.repository.UserRepository;
import com.dh.webservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @title User 컨트롤러 파일
 * @author cdh
 * @FileName : UserController
 *
 */
@Controller
@RequestMapping(value="/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 회원 가입 페이지
     * @return 회원 가입 페이지 뷰
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/signup");
        return modelAndView;
    }

    /**
     * 회원 가입 페이지
     * @return 회원 가입 페이지
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView signup(@RequestBody User user) {

        ModelAndView modelAndView = new ModelAndView();
        userService.saveUser(user);
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("/user/signup?return=true");

        return modelAndView;
    }


    /**
     * 인덱스 페이지
     * @return user main page view
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void index(Model model) throws Exception {
        System.out.println("get user index(); ");
        List<Goods> list = goodsRepository.findAll();
        System.out.println("goodsList.toStrong () +  " + list.toString());
        int listCount = list.size();
        System.out.println("goodsList.size () +  " + listCount);
        model.addAttribute("list", list);
        model.addAttribute("listCount", listCount);

    }

}