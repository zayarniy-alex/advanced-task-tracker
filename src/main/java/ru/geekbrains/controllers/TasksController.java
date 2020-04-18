package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entities.Task;
import ru.geekbrains.services.ProjectService;
import ru.geekbrains.services.TaskHistoryService;
import ru.geekbrains.services.TasksService;
import ru.geekbrains.services.UserService;
import ru.geekbrains.utils.TaskFilter;

import java.security.Principal;
import java.text.ParseException;
import java.util.Map;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    private TasksService tasksService;
    private ProjectService projectService;
    private UserService userService;
    private TaskHistoryService taskHistoryService;

    public TasksController(TasksService tasksService, ProjectService projectService, UserService userService, TaskHistoryService taskHistoryService) {
        this.tasksService = tasksService;
        this.projectService = projectService;
        this.userService = userService;
        this.taskHistoryService = taskHistoryService;
    }

    @GetMapping("/")
    public String showTasks(Model model, Principal principal, @RequestParam Map<String, String> params) throws ParseException {

        int pageIndex = 0;
        if (params.containsKey("p")) {
            pageIndex = Integer.parseInt(params.get("p")) - 1;
        }

        params.put("manager_id", userService.getUser(principal.getName()).getId().toString());
        params.put("employer_id", userService.getUser(principal.getName()).getId().toString());

        Pageable pageRequest = PageRequest.of(pageIndex, 10);
        TaskFilter taskFilter = new TaskFilter(params);
        Page<Task> page = tasksService.findAllSpec(taskFilter.getSpec(), pageRequest);

        model.addAttribute("projectList", projectService.findAll());
        model.addAttribute("filtersDef", taskFilter.getFilterDefinition());
        model.addAttribute("page", page);
        return "tasks/tasks-list";
    }

    @GetMapping("/create")
    public String createTask(Model model, Principal principal, @ModelAttribute(name = "task") Task task) {
        task.setManager_id(userService.getUser(principal.getName()).getId());
        task.setStatus(Task.Status.CREATED);
        task.setProgress(0);
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
    public String editTask(Model model, Principal principal, @RequestParam(name = "id", required = false) Long id) throws Exception {
        Task task = null;
        if (id != null) {
            task = tasksService.findById(id);
        } else {
            throw new Exception("Id отсутствует");
        }
        model.addAttribute("editor", userService.getUser(principal.getName()));
        model.addAttribute("task", task);
        model.addAttribute("projectList", projectService.findAll());
        model.addAttribute("userList", userService.findAll());
        return "tasks/edit-task";
    }

    @PostMapping("/edit")
    public String saveModifiedProduct(@ModelAttribute(name = "task") Task task, Principal principal) {
        tasksService.save(task, principal);
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
        model.addAttribute("taskHistory", taskHistoryService.findByTaskId(id));
        return "tasks/show-task";
    }

}
