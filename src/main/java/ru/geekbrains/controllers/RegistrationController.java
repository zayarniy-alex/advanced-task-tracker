package ru.geekbrains.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.UserDTO;
import ru.geekbrains.services.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping ("/registration")
public class RegistrationController {

    private UserService userService;
    private static final String USERNAME_ERR_MSG = "Пользователь с таким именем уже существует";
    private static final String PASSWORD_ERR_MSG = "Пароли не совпадают";


    @Autowired
    public void setUserService(UserService service) {
        userService = service;
    }


    @ModelAttribute ("user")
    public UserDTO user() {
        return new UserDTO();
    }


    @GetMapping
    public String registrationPage() {
        return "registration";
    }


    @PostMapping
    public String registration(@ModelAttribute ("user") @Valid UserDTO user,
                               BindingResult bindRes, Model model) {
        if (bindRes.hasErrors())
            return "registration";

        if (userService.isExistUser(user)) {
            model.addAttribute("error", USERNAME_ERR_MSG);
            return "registration";
        }

        if (!user.password.equals(user.matchingPassword)) {
            model.addAttribute("error", PASSWORD_ERR_MSG);
            return "registration";
        }

        userService.registrateUser(user);

        return "redirect:/";
    }


}