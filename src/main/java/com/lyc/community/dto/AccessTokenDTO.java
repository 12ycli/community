package com.lyc.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:DTO：数据传输模型；
 * @author: liyuecheng
 * @create: 2019-10-01 23:05
 **/
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
