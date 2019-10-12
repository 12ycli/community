package com.lyc.community.controller;

import com.lyc.community.dto.PageDTO;
import com.lyc.community.dto.QuestionDTO;
import com.lyc.community.mapper.QuestionMapper;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.Question;
import com.lyc.community.model.User;
import com.lyc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Autowired
    private QuestionService questionService;

    @GetMapping({"/","/index"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "5") Integer size,
                        @RequestParam(name="search",required = false) String search){
        // User user = (User)request.getSession().getAttribute("user");

        PageDTO<QuestionDTO> pageDTO = questionService.list(search, page, size);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("search",search);
        return "index";
    }
}
