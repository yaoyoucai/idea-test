package com.sso.vip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

//controller 与restController的区别
@Controller
@RequestMapping("/view")
public class ViewController {
    @Autowired
    public RestTemplate restTemplate;

    //值一定要为value
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@CookieValue(required = false, value = "TOKEN") Cookie cookie, HttpSession session) {
        if (cookie != null) {
            String token = cookie.getValue();
            Map value = restTemplate.getForObject("http://login.codeshop.com:8082/login/info?token=" + token, Map.class);
            session.setAttribute("loginUser",value);
        }
        return "index";
    }
}
