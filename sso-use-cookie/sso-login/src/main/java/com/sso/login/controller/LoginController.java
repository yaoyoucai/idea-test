package com.sso.login.controller;

import com.sso.login.entity.User;
import com.sso.login.utils.LoginCacheUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static Set<User> dbUsers;
    static {
        dbUsers = new HashSet<>();
        dbUsers.add(new User(1,"zhangsan","12345"));
        dbUsers.add(new User(2,"lisi","123456"));
        dbUsers.add(new User(3,"wangwu","1234567"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(User user, HttpSession session, HttpServletResponse response) {
        Optional<User> first = dbUsers.stream().filter(dbUser -> user.getPassword().equals(dbUser.getPassword())
                && user.getUsername().equals(dbUser.getUsername()))
                .findFirst();
        //数据库中用户存在
        if (first.isPresent()) {
            String token = UUID.randomUUID().toString();
            LoginCacheUtils.loginUsers.put(token, first.get());

            Cookie cookie = new Cookie("TOKEN",token);
            cookie.setDomain("codeshop.com");
            response.addCookie(cookie);
        } else {
            session.setAttribute("msg","用户名或者密码错误");
            return "login";
        }

        String target= (String) session.getAttribute("target");
        return "redirect:"+target;
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> info(String token) {
        if (token != null) {
            User user = LoginCacheUtils.loginUsers.get(token);
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

    }
}
