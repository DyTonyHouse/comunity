package com.maurice.community.advice;

import com.maurice.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
@ControllerAdvice
public class CustomizeExceptionHandler{

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
//        HttpStatus status = getStatus(request);
        if (ex instanceof CustomizeException){
            model.addAttribute("message", ex.getMessage());
        }else {
            model.addAttribute("message", "服务器太热啦，要不换个请求试试？");
        }
        return new ModelAndView("error");
    }


}
