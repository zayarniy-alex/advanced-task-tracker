package ru.geekbrains.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.geekbrains.entities.Notification;
import ru.geekbrains.entities.Task;
import ru.geekbrains.events.TaskCreatedEvent;
import ru.geekbrains.repositories.NotificationRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;
import static ru.geekbrains.entities.Notification.Status.UNCHECKED;


@Component
public class NotificationGenerator
{

  private NotificationRepository notificationRepo;
  private ScheduledExecutorService exec;
  @Value("${server.servlet.context-path}")
  private String context;

  private static final int WORK_HOUR = 1;
  private static final int WORK_MINUTE = 0;
  private static final long MINUTES_IN_DAY = TimeUnit.DAYS.toMinutes(1);


  @Autowired
  public void setNotificationRepository(NotificationRepository nr)
  {
	notificationRepo = nr;
  }


  @PostConstruct
  public void init()
  {
	exec = newScheduledThreadPool(1);

	LocalDate today = LocalDate.now();
	LocalDateTime now = LocalDateTime.now();
	LocalDateTime targetToday = today.atTime(WORK_HOUR, WORK_MINUTE);
	LocalDateTime targetTomorrow = today.plusDays(1).atTime(WORK_HOUR, WORK_MINUTE);

	long minUntilTomorrow = now.until(targetTomorrow, ChronoUnit.MINUTES);
	long minUntilToday = now.until(targetToday, ChronoUnit.MINUTES);
	long initDelay = minUntilTomorrow <= MINUTES_IN_DAY ?
					 minUntilTomorrow :
					 minUntilToday;

	exec.scheduleAtFixedRate(
			this::createHighUrgencyTaskNotifications,
			initDelay, MINUTES_IN_DAY, TimeUnit.MINUTES);
  }


  @PreDestroy
  public void done()
  {
	exec.shutdownNow();
  }


  private void createHighUrgencyTaskNotifications()
  {
	;
  }


  @Async
  @TransactionalEventListener(phase = BEFORE_COMMIT)
  public void createTaskCreatedNotification(TaskCreatedEvent e)
  {
	Task task = e.getTask();
	Long taskId = task.getId();
	Long projId = task.getProject_id();
	Long userId = task.getEmployer_id();
	String taskTitle = task.getTitle();

	Notification notif = new Notification();
	notif.setStatus(UNCHECKED);
	notif.setTask_id(taskId);
	notif.setProject_id(projId);
	notif.setReceiver_id(userId);
	notif.setDate_create(new Date());
	notif.setData("<p>Вам назначена новая задача<br>" +
				  "<a href='" + context + "/tasks/show?id=" + taskId + "'>" +
				  taskTitle + "</a></p>");

	notificationRepo.save(notif);
  }

}