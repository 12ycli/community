package com.lyc.community.dto;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-02 10:52
 **/
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
