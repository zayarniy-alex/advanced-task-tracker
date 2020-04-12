package ru.geekbrains.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Notification;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  @Query(value = "select n from Notification n where n.receiver_id = :userId")
  List<Notification> findAllByUserId(@Param("userId") Long userId);

}
