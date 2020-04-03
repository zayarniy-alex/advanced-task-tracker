package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.entities.Task;
import ru.geekbrains.services.ProjectService;
import ru.geekbrains.services.TasksService;
import ru.geekbrains.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private TasksService tasksService;
    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public void setTasksService(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showTasks(Model model) {
        List<Task> tasksList = tasksService.findAll();
        model.addAttribute("tasks", tasksList);
        return "tasks";
    }

    @GetMapping("/create")
    public String createTask(Model model, @ModelAttribute(name = "task") Task task) {

        model.addAttribute("urgencyList", task.getUrgency().values());
        model.addAttribute("statusList", task.getStatus().values());
        model.addAttribute("projectList", projectService.findAll());
        model.addAttribute("userList", userService.findAll());
        return "create-task";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute(name = "task") Task task) {
        tasksService.save(task);
        return "redirect:/tasks/";
    }

}
