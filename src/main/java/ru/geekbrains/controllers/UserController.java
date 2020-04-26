package ru.geekbrains.controllers;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.EmailDTO;
import ru.geekbrains.dto.NewPasswordDTO;
import ru.geekbrains.dto.UpdatePasswordDTO;
import ru.geekbrains.entities.User;
import ru.geekbrains.security.SecurityService;
import ru.geekbrains.services.EmailService;
import ru.geekbrains.services.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.Objects;
import java.util.UUID;

import static ru.geekbrains.utils.HttpRequestUtils.getAppUrl;


@Controller
@RequestMapping ("/user")
public class UserController {

    private UserService userService;
    private EmailService mailService;
    private SecurityService securityService;


    @Autowired
    public void setUserService(UserService service) {
        userService = service;
    }


    @Autowired
    public void setMailService(EmailService service) {
        mailService = service;
    }


    @Autowired
    @Qualifier("SecurServ")
    public void setSecurityService(SecurityService service) {
        securityService = service;
    }


    @ModelAttribute ("password")
    public UpdatePasswordDTO pwd() {
        return new UpdatePasswordDTO();
    }


    @ModelAttribute("emailObj")
    public EmailDTO emailObj() {
        return new EmailDTO();
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

    @GetMapping("/resetPassword")
    public String resetPasswordPage()
    {
        return "resetPassword";
    }


    @SneakyThrows
    @PostMapping("/resetPassword")
    public String resetPassword(HttpServletRequest req,
                                @ModelAttribute("emailObj") @Valid EmailDTO emailObj,
                                BindingResult bindRes, Model model)
    {
        if (bindRes.hasErrors())
            return "resetPassword";

        if (!userService.isExistUser(emailObj.username))
        {
            model.addAttribute("error", "Неверное имя пользователя");
            return "resetPassword";
        }

        sendResetPasswordEmail(req, emailObj);
        model.addAttribute("success", "Письмо для сброса пароля успешно отправлено");
        return "resetPassword";
    }


    private void sendResetPasswordEmail(HttpServletRequest req, EmailDTO emailObj)
    throws MalformedURLException, MessagingException
    {
        String appUrl = getAppUrl(req).toExternalForm();
        User user = userService.getUser(emailObj.username);
        String token = UUID.randomUUID().toString();

        String url = appUrl + "/user/resetPassword/new?" +
                     "id=" + user.getId() + "&token=" + token;

        String html = "<p>Для восстановления пароля перейдите по ссылке</p>" +
                      "<a href='" + url + "'>reset password</a>";

        userService.createPasswordResetToken(user, token);
        mailService.sendHTMLmessage(emailObj.email, "Восстановление пароля", html);
    }


    @GetMapping("/resetPassword/new")
    public String newPasswordPage(Model model, @RequestParam("id") long id,
                                  @RequestParam("token") String token)
    {
        try
        {
            securityService.validatePasswordResetToken(id, token);
        }
        catch (SecurityService.PasswordResetTokenException e)
        {
            model.addAttribute("message", e.getMessage());
            return "/login";
        }

        model.addAttribute("newPassword", new NewPasswordDTO());
        return "/newPassword";
    }


    @PostMapping("/resetPassword/save")
    @PreAuthorize("hasAuthority('CHANGE__PASSWORD__PRIVILEGE')")
    public String savePassword(@ModelAttribute("newPassword") @Valid NewPasswordDTO password,
                               BindingResult bindRes, Model model)
    {
        if (bindRes.hasErrors())
            return "newPassword";

        if (!Objects.equals(password.newpassword, password.matchingPassword))
        {
            model.addAttribute("error", "Повторенный пароль не совпадает с введенным");
            return "newPassword";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        userService.updatePassword(user, password.newpassword);
        model.addAttribute("success", "пароль успешно сохранен");

        return "newPassword";
    }


}