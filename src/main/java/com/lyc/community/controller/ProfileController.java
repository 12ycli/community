package com.lyc.community.controller;

import com.lyc.community.dto.PageDTO;
import com.lyc.community.model.User;
import com.lyc.community.service.NotificationService;
import com.lyc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-04 18:18
 **/
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="action")String action,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size,
                          Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
            PageDTO pageDTO = questionService.list(user.getId(),page,size);
            model.addAttribute("pageDTO",pageDTO);
        }else if("replies".equals(action)){
            PageDTO pageDTO = notificationService.list(user.getId(), page, size);
            Long unreadCount = notificationService.getUnreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pageDTO",pageDTO);
            model.addAttribute("unreadCount", unreadCount);
        }

        return "profile";
    }


}
