package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.TaskHistory;
import ru.geekbrains.repositories.TaskHistoryRepository;

@Service
public class TaskHistoryService {

    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    public void setTaskHistoryRepository(TaskHistoryRepository taskHistoryRepository) {
        this.taskHistoryRepository = taskHistoryRepository;
    }

    public TaskHistory save(TaskHistory taskHistory) {
        return taskHistoryRepository.save(taskHistory);
    }
}
