package com.maurice.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Maurice
 * @Date: 2019/8/10
 * @Description:
 */
@Controller
public class HelloController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
