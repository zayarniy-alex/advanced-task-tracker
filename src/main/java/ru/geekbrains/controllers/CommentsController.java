package ru.geekbrains.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.geekbrains.entities.Comment;
import ru.geekbrains.entities.CommentFilter;
import ru.geekbrains.repositories.CommentRepository;
import ru.geekbrains.services.CommentsService;
import ru.geekbrains.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class CommentsController {
    private final CommentRepository commentRepository;
    private CommentsService commentsService;
    private UserService userService;

    @Autowired
    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository=commentRepository;
    }

    @Autowired
    public void setCommentsService(CommentsService commentsService) {
        this.commentsService=commentsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/comments")
    public String commentsFilter(Model model,
                                 @ModelAttribute("filter") CommentFilter filter
    )
    {
        model.addAttribute("activePage", "Comments");
        model.addAttribute("filter", filter);
        model.addAttribute("comments", commentsService.getCommentsFilter(filter));
        model.addAttribute("users", userService.getUserList());
        model.addAttribute("taskIdN", filter.getIdTask());
        return "comments";
    }

    @GetMapping(value="/comments/delete")
    public void delete(Model model, @RequestParam("id") Long id
            , HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        model.addAttribute("activePage", "Comments");
        Comment comment=commentRepository.findById(id).get();
        commentRepository.deleteById(id);
        response.sendRedirect(request.getHeader("referer"));
    }

    @PostMapping("/comments/save")
    public void save(Model model, RedirectAttributes redirectAttributes
            , Principal principal,
                     @ModelAttribute("comment") Comment comment
            , HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        model.addAttribute("activePage", "Comments");
        try {
            commentsService.save(comment,principal);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", true);
        }
        response.sendRedirect(request.getHeader("referer"));
    }

}
