package com.lyc.community.controller;

import com.lyc.community.dto.QuestionDTO;
import com.lyc.community.mapper.QuestionMapper;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.Question;
import com.lyc.community.model.User;
import com.lyc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-03 20:05
 **/
@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable("questionId")Long questionId,
                       Model model){
        QuestionDTO question = questionService.findQuestionById(questionId);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("questionId",questionId);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "questionId", required = false) Long id,
                            HttpServletRequest request,
                            Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(StringUtils.isEmpty(title)){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        // if(StringUtils.isEmpty(description)){
        //     model.addAttribute("error","描述不能为空");
        // }
        //
        // if(StringUtils.isEmpty(tag)){
        //     model.addAttribute("error","标签不能为空");
        // }

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        questionService.update(question);
        return "redirect:/";
    }
}




