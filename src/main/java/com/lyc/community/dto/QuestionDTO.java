package com.lyc.community.dto;

import com.lyc.community.model.Question;
import com.lyc.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-04 09:55
 **/
@Data
public class QuestionDTO extends Question {
    private User user;
}
