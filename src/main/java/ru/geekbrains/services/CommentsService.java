package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Comment;
import ru.geekbrains.entities.CommentFilter;
import ru.geekbrains.repositories.CommentRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CommentsService {

    private CommentRepository commentRepository;
    private CommentsService commentsService;
    private UserService userService;


    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository=commentRepository;
    }

    @Autowired
    public void setCommentsService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public Comment save(Comment comment,Principal principal) {
        comment.setDate_create(new Date());
        comment.setAuthor_id(userService.getUser(principal.getName()).getId());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsFilter(CommentFilter filter){
        CommentFilter localFilter=new CommentFilter();
        localFilter=filter;

        if (localFilter.getIdAuthor()==null)
            localFilter.setIdAuthor(0L);

        if (localFilter.getData() == null)
            localFilter.setData(null);
        else


        if (localFilter.getData().isEmpty()) {
            localFilter.setData(null);
        }

        if (localFilter.getDateFrom()==null){
            LocalDate date =  LocalDate.now().minusDays(30);
            localFilter.setDateFrom(java.sql.Date.valueOf(date));

        }
        if (localFilter.getDateTill()==null){
            LocalDate date =  LocalDate.now().plusDays(1);
            localFilter.setDateTill(java.sql.Date.valueOf(date));
        }

        List<Comment> list=commentRepository.findByFilter(localFilter.getIdTask(), localFilter.getData(), localFilter.getIdAuthor(), localFilter.getDateFrom(), localFilter.getDateTill());

        return list;

    }
}
