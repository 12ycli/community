package com.lyc.community.controller;

import com.lyc.community.dto.AccessTokenDTO;
import com.lyc.community.dto.GitHubUser;
import com.lyc.community.mapper.UserMapper;
import com.lyc.community.model.User;
import com.lyc.community.provider.GitHubProvider;
import com.lyc.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-01 22:54
 **/
@Controller
public class AuthorizeController {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam("code")String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser.getId()!=null){
            // 登陆成功
            String token = UUID.randomUUID().toString();
            userService.update(gitHubUser, token);
            response.addCookie(new Cookie("token", token));
            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        } else {
            // 登陆失败
            System.out.println("登陆失败！");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                  HttpServletResponse response){
        request.getSession().removeAttribute("user");
        //去掉cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
