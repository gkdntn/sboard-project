package com.sboard.controller;

import com.sboard.config.AppInfo;
import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.entity.Terms;
import com.sboard.entity.User;
import com.sboard.service.TermsService;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final TermsService termsService;

    private final AppInfo appInfo;

    @GetMapping("/user/login")
    public String login(Model model) {

        model.addAttribute(appInfo);

        return "/user/login";
    }

    @GetMapping("/user/register")
    public String register() {
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(UserDTO userDTO){
        userService.insertUser(userDTO);

        return "redirect:/user/login";
    }


    @GetMapping("/user/terms")
    public String terms(Model model) {

        Terms terms = termsService.selectUsers();

        model.addAttribute("terms", terms);

        return "/user/terms";
    }



}
