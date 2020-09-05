package com.jonny.spring.boot.blog.initializrstart.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class UserController {

    public ModelAndView userlist(Model model){
        ModelAndView modelAndView = new ModelAndView("users/list","usersModel",model);


        return modelAndView;
    }

}
