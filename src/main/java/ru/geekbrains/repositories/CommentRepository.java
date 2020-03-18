package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
