package com.lyc.community.service;

import com.lyc.community.dto.GitHubUser;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.User;
import com.lyc.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-05 15:58
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void update(GitHubUser gitHubUser, String token) {
        String AccountId = String.valueOf(gitHubUser.getId());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(AccountId);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()==0){
            User user = new User();
            //插入数据库
            long now = System.currentTimeMillis();
            user.setGmtCreate(now);
            user.setGmtModified(now);
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(AccountId);
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userMapper.insert(user);
        }else{
            User dbUser = users.get(0);
            User user = new User();
            //插入数据库
            long now = System.currentTimeMillis();
            user.setGmtModified(now);
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(user,userExample);
        }
    }
}
