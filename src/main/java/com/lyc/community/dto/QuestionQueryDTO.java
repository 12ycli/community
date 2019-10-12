package com.lyc.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-12 17:50
 **/
@Data
public class QuestionQueryDTO {

    private String search;
    private Integer page;
    private Integer size;

}
