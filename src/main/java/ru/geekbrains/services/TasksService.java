package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Task;
import ru.geekbrains.entities.TaskHistory;
import ru.geekbrains.repositories.TasksRepository;

import java.security.Principal;
import java.util.List;

@Service
public class TasksService {

    private TasksRepository tasksRepository;

    private UserService userService;
    private TaskHistoryService taskHistoryService;

    @Autowired
    public void setTasksRepository(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTaskHistoryService(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
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

    public Task save(Task task, Principal principal) {

        if (task.getId() != null) {
            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setTask_id(task.getId());
            // что изменилось в задаче
            Task currentTask = this.findById(task.getId());
            StringBuilder description = new StringBuilder();
            boolean isEdited = false;
            if (!task.getTitle().equals(currentTask.getTitle())) {
                isEdited = true;
                description.append("Название изменено с <b>" + currentTask.getTitle() + "</b> на " + "<b>" + task.getTitle() + "</b>");
            }

            if (isEdited) {
                description.insert(0, "Пользователь " + userService.getUser(principal.getName()).getFirstname()
                        + " " + userService.getUser(principal.getName()).getLastname()
                        + " внес изменения: ");
            }
            taskHistory.setDescription(description.toString());
            taskHistory.setUser_id(userService.getUser(principal.getName()).getId());
            taskHistoryService.save(taskHistory);
        }
        return tasksRepository.save(task);
    }
}
