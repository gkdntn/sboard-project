package com.sboard.controller;

import com.sboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class RegisterController {

    private final UserService userService;

    @GetMapping("/checkUser")
    public int checkUser(@RequestParam(required = false) String type, @RequestParam(required = false) String value, HttpServletRequest httpServletRequest) {

        log.info("1111111111111111111111111"+type);
        int result = 0;
            result = userService.selectCountUser(type,value);

        if(type.equals("email") && result == 0) {
            String code = userService.sendEmailCode(value);

            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("authCode", code);
        log.info("2222222222222222222222222"+code);

        }

        return result;

    }

    @PostMapping("/checkUser")
    public int checkUser(@RequestBody String code, HttpServletRequest httpServletRequest){

        int result = 0;
        log.info("23123123123123123123123123"+code);

        HttpSession session = httpServletRequest.getSession();
        String authCode = (String) session.getAttribute("authCode");
        log.info("33333333333333333333333"+authCode);

        if(authCode.equals(code)) {
            result = 1;
        log.info("33333333333333333333333444--"+result);
        }

        return result;
    }


}
