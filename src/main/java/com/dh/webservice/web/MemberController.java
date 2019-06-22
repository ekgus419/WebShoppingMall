package com.dh.webservice.web;

import com.dh.webservice.domain.Member;
import com.dh.webservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("member/login");
        return modelAndView;
    }


    @RequestMapping(value = "/member/signup", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Member member = new Member();
        modelAndView.addObject("member", member);
        modelAndView.setViewName("member/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/member/signup", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createNewUser(@RequestBody Member member) {

        System.out.println("등록 post");
        System.out.println("1. + " + member.toString());

        ModelAndView modelAndView = new ModelAndView();

        memberService.saveMember(member);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("member", new Member());
        modelAndView.setViewName("/member/signup?return=true");

        return modelAndView;
    }


    @RequestMapping(value = "/member/admin/index", method = RequestMethod.GET)
    public ModelAndView adminhome() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findUserByUserEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " +  member.getUserName() + " (" + member.getUserEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("member/admin/index");
        return modelAndView;
    }


    @RequestMapping(value = "/member/index", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberService.findUserByUserEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + member.getUserName() + " (" + member.getUserEmail() + ")");
        modelAndView.addObject("memberMessage", "Content Available Only for Users with User Role");
        modelAndView.setViewName("member/index");
        return modelAndView;
    }

}