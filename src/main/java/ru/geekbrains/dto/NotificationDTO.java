package ru.geekbrains.dto;


import lombok.Data;
import ru.geekbrains.entities.Notification;
import ru.geekbrains.entities.Notification.Status;

import java.util.Date;


@Data
public class NotificationDTO
{

  private Long id;
  private Status status;
  private String comment;
  private Date creationDate;


  public NotificationDTO(Notification notif)
  {
	id = notif.getId();
	status = notif.getStatus();
	comment = notif.getData();
	creationDate = notif.getDate_create();
  }


  public boolean isUnchecked()
  {
	return status == Status.UNCHECKED;
  }

}