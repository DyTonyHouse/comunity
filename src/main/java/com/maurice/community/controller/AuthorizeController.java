package com.maurice.community.controller;

import com.maurice.community.entity.AccessToken;
import com.maurice.community.entity.GithubUser;
import com.maurice.community.entity.User;
import com.maurice.community.provider.GithubProvider;
import com.maurice.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse servletResponse,
                           HttpServletRequest servletRequest){
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirect_uri(redirectUri);
        accessToken.setCode(code);
        accessToken.setState(state);

        String accessId = githubProvider.getAccessToken(accessToken);

        GithubUser githubUser = githubProvider.getGithubUser(accessId);

        if (githubUser != null) {
            User user = userService.findByAccessId(githubUser.getId());
            //插入数据库的过程相当于写入 session
            userService.createOrUpdate(user, githubUser, servletResponse, servletRequest);

            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
