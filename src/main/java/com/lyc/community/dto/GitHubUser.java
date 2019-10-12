package com.lyc.community.dto;

import lombok.Data;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-02 10:52
 **/
@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
