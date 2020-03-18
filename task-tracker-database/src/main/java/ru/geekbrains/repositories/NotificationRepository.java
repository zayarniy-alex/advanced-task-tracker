package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
