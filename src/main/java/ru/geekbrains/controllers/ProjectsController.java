package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.geekbrains.entities.*;
import ru.geekbrains.repositories.ProjectRepository;
import ru.geekbrains.services.CommentsService;
import ru.geekbrains.services.ProjectService;
import ru.geekbrains.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class ProjectsController {
    private final ProjectRepository projectRepository;
    private ProjectService projectService;
    private UserService userService;

    @Autowired
    public ProjectsController(ProjectRepository projectRepository) {
        this.projectRepository=projectRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }



    @GetMapping(value="/projects")
    public String projectsFilter(Model model,
                                 @ModelAttribute("filter") ProjectFilter filter
    )
    {
        model.addAttribute("activePage", "Projects");
        model.addAttribute("filter", filter);
        if (filter.isNull()){
            model.addAttribute("projects",projectService.findAll());
        }
        else {
            model.addAttribute("projects",
                    projectService.findProjectFilter(filter.getManagerId(), filter.getTitle(), filter.getStatus()));
        }
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("statuses", Project.Status.values());
        return "projects";
    }

    @GetMapping(value="/project/create")
    public String createProject(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("activePage", "Projects");
        Project project=new Project();
        project.setStatus(Project.Status.CREATED);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("mode", "CREATE");
        return "project";
    }

    @PostMapping("/project/save")
    public String save(Model model, RedirectAttributes redirectAttributes
            , @ModelAttribute("project") Project project) throws IOException {
        model.addAttribute("activePage", "Projects");

        try {
            projectRepository.save(project);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
        }
        return "redirect:/projects";
    }

    @GetMapping(value="/project/delete")
    public String deleteDocument(Model model, @RequestParam("id") Long id) {
        model.addAttribute("activePage", "Projects");
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }

    @GetMapping("/project/edit")
    public String editProject(Model model, @RequestParam(name = "id") Long id) throws Exception {
        Project project=projectService.findById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("mode", "EDIT");
        return "project";
    }

    @GetMapping("/project/view")
    public String viewProject(Model model, @RequestParam(name = "id") Long id) throws Exception {
        Project project=projectService.findById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("mode", "VIEW");
        return "project";
    }

}
