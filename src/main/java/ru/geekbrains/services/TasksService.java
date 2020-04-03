package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return tasksRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Task findById(Long id) {
        return tasksRepository.findById(id).get();
    }

    public Task save(Task task) {
        return tasksRepository.save(task);
    }
}
