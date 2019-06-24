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


@Controller
@RequestMapping(value="/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView signup(@RequestBody User user) {

        ModelAndView modelAndView = new ModelAndView();
        userService.saveUser(user);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("/user/signup?return=true");

        return modelAndView;
    }


    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public ModelAndView adminhome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " +  user.getUserName() + " (" + user.getUserEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("user/admin/index");
        return modelAndView;
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug(auth.toString());
        User user = userService.findUserByUserEmail(auth.getName());
        log.debug("user : " + user.toString());
        modelAndView.addObject("user", user);
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + " (" + user.getUserEmail() + ")");
        modelAndView.addObject("userMessage", "Content Available Only for Users with User Role");
        modelAndView.setViewName("user/index");
        return modelAndView;
    }

}