package com.lyc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-01 17:19
 **/
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam("name")String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }

}
