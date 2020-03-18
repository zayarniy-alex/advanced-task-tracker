package ru.geekbrains.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    private static final String LOGIN_FAIL_MSG = "Неверные имя пользователя и/или пароль";
    private static final String LOGOUT_MSG = "Вы вышли из системы";

    @GetMapping ("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping ("/loginfail")
    public String loginFail(Model uiModel) {
        uiModel.addAttribute("message", LOGIN_FAIL_MSG);
        return "login";
    }

    @RequestMapping ("/logout")
    public String logout(Model uiModel) {
        uiModel.addAttribute("message", LOGOUT_MSG);
        return "login";
    }
}