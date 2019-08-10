package com.maurice.community.controller;

import com.maurice.community.entity.AccessToken;
import com.maurice.community.entity.GithubUser;
import com.maurice.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Maurice
 * @Date: 2019/8/10
 * @Description:
 */
@Controller
public class AuthorizeController {

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirect_uri(redirectUri);
        accessToken.setCode(code);
        accessToken.setState(state);

        String token = githubProvider.getAccessToken(accessToken);
        GithubUser githubUser = githubProvider.getGithubUser(token);
        return "index";
    }
}
