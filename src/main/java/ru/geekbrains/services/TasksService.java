package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Task;
import ru.geekbrains.repositories.TasksRepository;

import java.util.List;

@Service
public class TasksService {

    private TasksRepository tasksRepository;

    @Autowired
    public void setTasksRepository(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Task> findAll() {
        return tasksRepository.findAll();
    }

    public Task save(Task task) {
        return tasksRepository.save(task);
    }


}
