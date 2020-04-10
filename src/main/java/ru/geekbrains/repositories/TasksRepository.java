package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Task;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.manager_id = :manager_id")
    List<Task> findByManager_id(@Param("manager_id") Long id);

    @Query("select t from Task t where t.employer_id = :employer_id")
    List<Task> findByEmployer_id(@Param("employer_id") Long id);

    @Query("select t from Task t where t.manager_id = :user_id or t.employer_id = :user_id")
    List<Task> findByManager_idAndEmployer_id(@Param("user_id") Long id);
}
