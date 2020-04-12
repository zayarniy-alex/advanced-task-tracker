package ru.geekbrains.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.dto.NotificationDTO;
import ru.geekbrains.entities.User;
import ru.geekbrains.services.NotificationService;
import ru.geekbrains.services.UserService;

import java.util.List;

import static java.util.Comparator.comparing;


@Controller
@RequestMapping("/notifications")
public class NotificationController
{

  private NotificationService notifService;
  private UserService userService;


  @Autowired
  public void setNotificationService(NotificationService service)
  {
	notifService = service;
  }


  @Autowired
  public void setUserService(UserService service)
  {
	userService = service;
  }


  @GetMapping
  public String notificationsPage(Model ui)
  {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	User user = userService.getUser(auth.getName());

	List<NotificationDTO> notifications = notifService.getAllNotifications(user.getId());
	notifications.sort(
			comparing(NotificationDTO::getStatus)
					.thenComparing(NotificationDTO::getCreationDate));

	ui.addAttribute("notifications", notifications);
	return "notifications";
  }

}