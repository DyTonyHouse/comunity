package com.maurice.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
@Controller
@RequestMapping(path = "/error")
public class CustomizeErrorContorller implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model){
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("message", "您这个请求错了吧，要不换个姿势？");
        }else if (status.is5xxServerError()){
            model.addAttribute("message", "服务器太热啦，要不换个请求试试？");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
