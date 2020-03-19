package ru.geekbrains.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.TaskHistory;

@Repository
public interface TaskHistoryRepository extends CrudRepository<TaskHistory, Long> {
}
