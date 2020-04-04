package ru.geekbrains.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.UpdatePasswordDTO;
import ru.geekbrains.entities.User;
import ru.geekbrains.services.UserService;

import javax.validation.Valid;
import java.util.Objects;


@Controller
@RequestMapping ("/user")
public class UserController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService service) {
        userService = service;
    }


    @ModelAttribute ("password")
    public UpdatePasswordDTO pwd() {
        return new UpdatePasswordDTO();
    }


    @GetMapping ("/updatePassword")
    public String changePasswordPage() {
        return "changePassword";
    }


    @PostMapping ("/updatePassword")
    @PreAuthorize ("hasRole('USER')")
    public String changeUserPassword(@ModelAttribute ("password") @Valid UpdatePasswordDTO pwd,
                                     BindingResult bindRes, Model model) {
        if (bindRes.hasErrors()) return "changePassword";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(auth.getName());

        if (!userService.checkPassword(user, pwd.oldpassword)) {
            model.addAttribute("error", "Неверный старый пароль");
            return "changePassword";
        }
        if (!Objects.equals(pwd.newpassword, pwd.matchingPassword)) {
            model.addAttribute("error", "Повторенный пароль не совпадает с введенным");
            return "changePassword";
        }

        userService.updatePassword(user, pwd.newpassword);
        model.addAttribute("success", "пароль успешно изменен");

        return "changePassword";
    }

}