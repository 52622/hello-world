package com.test.orange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by yangkang on 2018/12/23
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private final String basePath = "/login";
    @RequestMapping("")
    public String index(){
//        ModelAndView view = new ModelAndView(basePath + "/index");
//        return view;
        return basePath + "/index";
    }

    @RequestMapping("/doLogin")
    public String  doLogin(Model model, @RequestParam String account, @RequestParam String password) {
/*        ModelAndView mav = new ModelAndView(basePath + "/welcome");
        if ("root".equals(account) && "123456".equals(password)) {
            mav.addObject("account", account);
            return mav;
        }*/

        model.addAttribute("account", account);
        return basePath + "/welcome";
    }
}
