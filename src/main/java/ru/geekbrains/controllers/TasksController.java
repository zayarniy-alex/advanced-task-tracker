package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String addTask(@ModelAttribute(name = "task") Task task) {
        tasksService.save(task);
        return "redirect:/tasks/";
    }

    @GetMapping("/edit")
    public String editTask(Model model, @RequestParam(name = "id", required = false) Long id) {
        Task task = null;
        if (id != null) {
            task = tasksService.findById(id);
        } else {
            task = new Task();
        }
        model.addAttribute("task", task);
        model.addAttribute("urgencyList", task.getUrgency().values());
        model.addAttribute("statusList", task.getStatus().values());
        model.addAttribute("projectList", projectService.findAll());
        model.addAttribute("userList", userService.findAll());
        return "edit-task";
    }

    @PostMapping("/edit")
    public String saveModifiedProduct(@ModelAttribute(name = "task") Task task) {
        tasksService.save(task);
        return "redirect:/tasks/";
    }

}
