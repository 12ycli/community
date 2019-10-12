package com.lyc.community.controller;

import com.lyc.community.dto.CommentDTO;
import com.lyc.community.dto.NotificationDTO;
import com.lyc.community.dto.PageDTO;
import com.lyc.community.dto.QuestionDTO;
import com.lyc.community.enums.CommentTypeEnum;
import com.lyc.community.exception.CustomizeErrorCode;
import com.lyc.community.exception.CustomizeException;
import com.lyc.community.model.User;
import com.lyc.community.service.CommentService;
import com.lyc.community.service.NotificationService;
import com.lyc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-11 22:17
 **/
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="id")Long id){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);

        return "redirect:/question/"+notificationDTO.getOuterid();
    }

}
