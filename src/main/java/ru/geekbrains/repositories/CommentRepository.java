package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Comment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);

    @Query("select c " +
            "from Comment c " +
            "where c.task_id=:task_id order by c.date_create desc")
    List<Comment> findByTask(@Param("task_id") Long idTask);

    @Query("select c " +
            "from Comment c " +
            "where (c.task_id=:task_id) and " +
            " (c.data like :data or :data is null) and " +
            " (c.author_id=:author_id or :author_id=0) " +
            "  and c.date_create between :date_from and :date_till "+//coalesce(:date_from,to_date('2001-01-01','yyyy-mm-dd')) and coalesce(:date_till,current_date)  " +
            "order by c.date_create desc")
    List<Comment> findByFilter(@Param("task_id") Long idTask,@Param("data") String data, @Param("author_id") Long idAuthor,
                               @Param("date_from") Date dateFrom, @Param("date_till") Date dateTill
    );
}
