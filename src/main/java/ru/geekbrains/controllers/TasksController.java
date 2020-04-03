package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Task;
import ru.geekbrains.entities.User;
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

    public org.springframework.security.core.userdetails.User getCurrentUser() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping("/")
    public String showTasks(Model model) {
        List<Task> tasksList = tasksService.findAll();
        model.addAttribute("tasks", tasksList);
        return "tasks/tasks-list";
    }

    @GetMapping("/create")
    public String createTask(Model model, @ModelAttribute(name = "task") Task task) {
        task.setManager_id(userService.getUser(this.getCurrentUser().getUsername()).getId());
        model.addAttribute("urgencyList", task.getUrgency().values());
        model.addAttribute("statusList", task.getStatus().values());
        model.addAttribute("projectList", projectService.findAll());
        model.addAttribute("userList", userService.findAll());
        return "tasks/create-task";
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
        model.addAttribute("editor", userService.getUser(this.getCurrentUser().getUsername()));
        model.addAttribute("task", task);
        model.addAttribute("urgencyList", task.getUrgency().values());
        model.addAttribute("statusList", task.getStatus().values());
        model.addAttribute("projectList", projectService.findAll());
        model.addAttribute("userList", userService.findAll());
        return "tasks/edit-task";
    }

    @PostMapping("/edit")
    public String saveModifiedProduct(@ModelAttribute(name = "task") Task task) {
        tasksService.save(task);
        return "redirect:/tasks/";
    }

    @GetMapping("/show")
    public String showTask(Model model, @RequestParam(name = "id") Long id) throws Exception {
        Task task = null;
        if (id != null) {
            task = tasksService.findById(id);
        } else {
            throw new Exception("Id не указан");
        }
        model.addAttribute("task", task);
        model.addAttribute("manager", userService.findById(task.getManager_id()));
        model.addAttribute("employer", userService.findById(task.getEmployer_id()));
        model.addAttribute("project", projectService.findById(task.getProject_id()));
        return "tasks/show-task";
    }

}
