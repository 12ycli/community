package com.lyc.community.provider;

import com.alibaba.fastjson.JSON;
import com.lyc.community.dto.AccessTokenDTO;
import com.lyc.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-01 23:03
 **/
@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO dto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            // 返回的是
            String accessToken = string.split("&")[0].split("=")[1];
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
