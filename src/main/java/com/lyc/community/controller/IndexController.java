package com.lyc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-01 20:33
 **/
@Controller
public class IndexController {
    @GetMapping({"/","/index"})
    public String index(HttpServletRequest request){
        System.out.println("IndexController--index()");
        System.out.println("request.getSession().getAttribute-user:"+request.getSession().getAttribute("user"));
        return "index";
    }
}
