package com.lyc.community.controller;

import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-01 20:33
 **/
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping({"/","/index"})
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie c:cookies){
            if("token".equals(c.getName())){
                String token = c.getValue();
                User user = userMapper.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
