package com.sso.login.controller;

import com.sso.login.entity.User;
import com.sso.login.utils.LoginCacheUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

//controller 与restController的区别
@Controller
@RequestMapping("/view")
public class ViewController {
    //值一定要为value
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(String target, HttpSession session,
                        @CookieValue(required = false,value = "TOKEN") Cookie cookie) {
        if (StringUtils.isEmpty(target)) {
            target = "www.codeshop.com:8083";
        }

        if (cookie != null) {
            String token = cookie.getValue();
            User user = LoginCacheUtils.loginUsers.get(token);
            if (user != null) {
                return "redirect:"+target;
            }
        }
        session.setAttribute("target",target);
        return "login";
    }
}
