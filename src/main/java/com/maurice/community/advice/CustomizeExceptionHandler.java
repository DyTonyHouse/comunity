package com.maurice.community.advice;

import com.alibaba.fastjson.JSON;
import com.maurice.community.dto.ResultDTO;
import com.maurice.community.exception.CustomizeErrorCode;
import com.maurice.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @Author: Maurice
 * @Date: 2019/8/18
 * @Description:
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex, Model model) {
        if ("application/json".equals(request.getContentType())) {
            /*返回json数据
             * 先得到一个封装了code和message的对象，再使用response.getWrite写入客户端
             */
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioE) {
            }
            return resultDTO;
        } else {
            // 返回错误跳转
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", "服务器太热啦，要不换个请求试试？");
            }
            return new ModelAndView("error");
        }
    }

}
