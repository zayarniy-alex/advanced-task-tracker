package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Project;
import ru.geekbrains.entities.Task;
import ru.geekbrains.entities.TaskHistory;
import ru.geekbrains.entities.User;
import ru.geekbrains.repositories.TasksRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class TasksService {

    private TasksRepository tasksRepository;
    private UserService userService;
    private ProjectService projectService;
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
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setTaskHistoryService(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    public Page<Task> findAllSpec(Specification<Task> spec, Pageable pageable) {
        return tasksRepository.findAll(spec, pageable);
    }

    public List<Task> findAll() {
        return tasksRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Task findById(Long id) {
        return tasksRepository.findById(id).get();
    }

    public List<Task> findByManagerId(Long id) {
        return tasksRepository.findByManager_id(id);
    }

    public List<Task> findByEmployerId(Long id) {
        return tasksRepository.findByEmployer_id(id);
    }

    public List<Task> findByManager_idAndEmployer_id(Long id) {
        return tasksRepository.findByManager_idAndEmployer_id(id);
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
                description.append("Название изменено с '" + currentTask.getTitle() + "' на " + "'" + task.getTitle() + "'. ");
            }

            if (!task.getDescription().equals(currentTask.getDescription())) {
                isEdited = true;
                description.append("Описание изменено с '" + currentTask.getDescription() + "' на " + "'" + task.getDescription() + "'. ");
            }

            if (!task.getEmployer_id().equals(currentTask.getEmployer_id())) {
                isEdited = true;
                User currentEmployer = userService.findById(currentTask.getEmployer_id());
                User newEmployer = userService.findById(task.getEmployer_id());
                description.append("Исполнитель изменен с '" + currentEmployer.getLastname() + " " + currentEmployer.getFirstname()
                            + "' на " + "'" + newEmployer.getLastname() + " " + newEmployer.getFirstname() + "'. ");
            }

            if (!task.getDue_time().equals(currentTask.getDue_time())) {
                isEdited = true;
                description.append("Дата завершения изменена с '" + currentTask.getDue_time() + "' на " + "'" + task.getDue_time() + "'. ");
            }

            if (!task.getPlan_time().equals(currentTask.getPlan_time())) {
                isEdited = true;
                description.append("Время выполнения изменилось с '" + currentTask.getPlan_time() + "ч' на " + "'" + task.getPlan_time() + "ч'. ");
            }

            if (Float.compare(task.getProgress(), currentTask.getProgress()) != 0) {
                isEdited = true;
                description.append("Прогресс изменился с '" + currentTask.getProgress() + "%' на " + "'" + task.getProgress() + "%'.");
            }

            if (!task.getProject_id().equals(currentTask.getProject_id())) {
                isEdited = true;
                Project currentProject = projectService.findById(currentTask.getProject_id());
                Project newProject = projectService.findById(task.getProject_id());
                description.append("Проект изменен с '" + currentProject.getTitle()
                        + "' на " + "'" + newProject.getTitle() + "'. ");
            }

            if (!task.getUrgency().equals(currentTask.getUrgency())) {
                isEdited = true;
                description.append("Срочность изменилась с '" + currentTask.getUrgency() + "' на " + "'" + task.getUrgency() + "'. ");
            }

            if (!task.getStatus().equals(currentTask.getStatus())) {
                isEdited = true;
                description.append("Статус изменился с '" + currentTask.getStatus() + "' на " + "'" + task.getStatus() + "'. ");
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
