package com.lyc.community.dto;

import lombok.Data;


/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-06 13:30
 **/
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
