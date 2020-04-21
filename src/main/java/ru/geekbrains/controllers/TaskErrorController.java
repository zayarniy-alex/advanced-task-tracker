package ru.geekbrains.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TaskErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletResponse response, Model model) {
        model.addAttribute("error", response.getStatus());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
