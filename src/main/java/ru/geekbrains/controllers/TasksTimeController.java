package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.geekbrains.entities.TaskTime;
import ru.geekbrains.entities.TaskTimeFilter;
import ru.geekbrains.repositories.TasksTimeRepository;
import ru.geekbrains.services.TaskTimeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Controller
public class TasksTimeController {
    private final TasksTimeRepository tasksTimeRepository;
    private TaskTimeService taskTimeService;

    @Autowired
    public TasksTimeController(TasksTimeRepository tasksTimeRepository) {
        this.tasksTimeRepository=tasksTimeRepository;
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
    public void save(
                     RedirectAttributes redirectAttributes
                        , Principal principal,
                       @ModelAttribute("ttime")TaskTime taskTime
                        , HttpServletRequest request, HttpServletResponse response
                       ) throws IOException {

        try {
            taskTimeService.save(taskTime,principal);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
        }
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping(value="/ttime/delete")
    public void delete(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        tasksTimeRepository.deleteById(id);
        response.sendRedirect(request.getHeader("referer"));
    }




}
