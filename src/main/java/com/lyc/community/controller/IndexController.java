package com.lyc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-01 20:33
 **/
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
