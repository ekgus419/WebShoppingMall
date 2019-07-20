/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.Payment;
import com.dh.webservice.domain.RetrunMessage;
import com.dh.webservice.domain.User;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.repository.PaymentRepository;
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

import java.security.Principal;
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

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/signIn")
    public ModelAndView signIn() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/signIn");
        return modelAndView;
    }


    /**
     * 회원 가입 페이지
     * @return 회원 가입 페이지 뷰
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/signUp");
        return modelAndView;
    }

    /**
     * 회원 가입 페이지
     * @return 회원 가입 페이지
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView signUp(@RequestBody User user) {

        ModelAndView modelAndView = new ModelAndView();
        userService.saveUser(user);
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("/user/signUp?return=true");

        return modelAndView;
    }

    /**
     * 상품 상세 보기
     * @param model
     * @param goodsNum
     * @return 상품 상세 페이지
     */
    @GetMapping("/view")
    public void getGoodsView(Model model, @RequestParam("goodsNum") Long goodsNum) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Goods goods = goodsRepository.findOne(goodsNum);
        User user = userService.findUserByUserEmail(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("goods", goods);

    }

    /**
     * 결제 데이터 누적
     * @param
     * @param
     * @return RetrunMessage
     */
    @PostMapping("/buy")
    @ResponseBody
    public RetrunMessage getGoodsBuy(@RequestBody Payment payment, Principal principal) throws Exception {
        System.out.println("toStr() ;" + payment.toString());
        System.out.println( "2?" + payment.getGoodsNum());

        RetrunMessage message = new RetrunMessage();

        String writer = principal.getName();
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            payment.setUserEmail(writer);
            paymentRepository.save(payment);
            message.setResult(true);
            message.setMessage("결제가 완료되었습니다.");
            return message;
        }else{
            message.setResult(false);
            message.setMessage("결제가 실패하였습니다.");
            return message;
        }
    }



}