package ru.geekbrains.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.NotificationDTO;
import ru.geekbrains.entities.Notification;
import ru.geekbrains.repositories.NotificationRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.geekbrains.entities.Notification.Status.CHECKED;


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
						   .collect(toList());
  }


  public void markNotificationsAsReaded(List<NotificationDTO> list)
  {
	List<Long> idList = list.stream()
							.map(NotificationDTO::getId)
							.collect(toList());

	List<Notification> notifications = notificationRepo.findAllById(idList);

	notifications.forEach(x -> x.setStatus(CHECKED));
	notificationRepo.saveAll(notifications);
  }

}