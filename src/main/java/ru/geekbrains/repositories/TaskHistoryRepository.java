package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.TaskHistory;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
}
