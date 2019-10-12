package com.lyc.community.dto;

import com.lyc.community.model.Comment;
import com.lyc.community.model.User;
import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-10 08:21
 **/
@Data
public class CommentDTO extends Comment {
    private User user;
}
