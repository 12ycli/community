package com.lyc.community.controller;

import com.lyc.community.dto.CommentDTO;
import com.lyc.community.dto.QuestionDTO;
import com.lyc.community.enums.CommentTypeEnum;
import com.lyc.community.exception.CustomizeErrorCode;
import com.lyc.community.exception.CustomizeException;
import com.lyc.community.service.CommentService;
import com.lyc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-05 14:48
 **/
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable("id")Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        if(questionDTO==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        questionService.increaseViewCount(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }

}
