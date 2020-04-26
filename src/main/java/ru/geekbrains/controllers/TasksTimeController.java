package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.geekbrains.entities.TaskTime;
import ru.geekbrains.entities.TaskTimeFilter;
import ru.geekbrains.repositories.TasksTimeRepository;
import ru.geekbrains.services.TaskTimeService;
import ru.geekbrains.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class TasksTimeController {
    private final TasksTimeRepository tasksTimeRepository;
    private UserService userService;
    private TaskTimeService taskTimeService;

    @Autowired
    public TasksTimeController(TasksTimeRepository tasksTimeRepository) {
        this.tasksTimeRepository=tasksTimeRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTaskTimeService(TaskTimeService taskTimeService) {
        this.taskTimeService=taskTimeService;
    }


    @GetMapping(value="/ttime")
    public String ttimeFilter(Model model,
                             @ModelAttribute("filter") TaskTimeFilter filter
    )
    {
        model.addAttribute("activePage", "Ttimes");
        model.addAttribute("filter", filter);
        model.addAttribute("taskTime", taskTimeService.getFilter(filter));
        model.addAttribute("taskIdN", filter.getIdTask());
        return "ttimes";
    }

    @PostMapping("/ttime/save")
    public void save(Model model, RedirectAttributes redirectAttributes
                        , Principal principal,
                       @ModelAttribute("ttime")TaskTime taskTime
                        , HttpServletRequest request, HttpServletResponse response
                       ) throws IOException {
        model.addAttribute("activePage", "Ttimes");

        try {
            taskTimeService.save(taskTime,principal);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
        }
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping(value="/ttime/delete")
    public void delete(Model model, @RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        model.addAttribute("activePage", "Ttimes");
        TaskTime taskTime=tasksTimeRepository.findById(id).get();
        tasksTimeRepository.deleteById(taskTime.getId());
        response.sendRedirect(request.getHeader("referer"));
    }




}
