/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.User;
import com.dh.webservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug(auth.toString());
        User user = userService.findUserByUserEmail(auth.getName());
        log.debug("user : " + user.toString());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/index");
        return modelAndView;
    }

}