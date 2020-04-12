package ru.geekbrains.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.NotificationDTO;
import ru.geekbrains.repositories.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class NotificationService
{

  private NotificationRepository notificationRepo;


  @Autowired
  public void setNotificationRepository(NotificationRepository nr)
  {
	notificationRepo = nr;
  }


  public List<NotificationDTO> getAllNotifications(Long userId)
  {
	return notificationRepo.findAllByUserId(userId)
						   .stream()
						   .map(NotificationDTO::new)
						   .collect(Collectors.toList());
  }

}