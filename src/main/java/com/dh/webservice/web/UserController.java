/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebShoppingmall)
 *
 */
package com.dh.webservice.web;

import com.dh.webservice.domain.Goods;
import com.dh.webservice.domain.Payment;
import com.dh.webservice.domain.ReturnResult;
import com.dh.webservice.domain.User;
import com.dh.webservice.repository.GoodsRepository;
import com.dh.webservice.repository.PaymentRepository;
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
     * @return ReturnResult
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult signUp(@RequestBody User user) {
        ReturnResult result = new ReturnResult();
        userService.saveUser(user);
        result.setResult(true);
        return result;
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
     * @param payment
     * @param principal
     * @return ReturnResult
     */
    @PostMapping("/buy")
    @ResponseBody
    public ReturnResult getGoodsBuy(@RequestBody Payment payment, Principal principal) throws Exception {

        ReturnResult result = new ReturnResult();

        String writer = principal.getName();
        if(!writer.equals("") &&  writer.trim().length() > 0) {
            if(payment.getFlag().equals("Yes")){
                payment.setUserEmail(writer);
                paymentRepository.save(payment);
                result.setResult(true);
            }else{
                Payment updatePayment = paymentRepository.findTopByOrderByPaymentNumDesc();
                updatePayment.setFlag("No");
                paymentRepository.save(updatePayment);
                result.setResult(false);
            }
            return result;
        }
        else{
            result.setResult(false);
            return result;
        }
    }



}