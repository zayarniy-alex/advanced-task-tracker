package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Task;

import java.util.Date;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query("select t from Task t where t.manager_id = :manager_id order by id")
    List<Task> findByManager_id(@Param("manager_id") Long id);

    @Query("select t from Task t where t.employer_id = :employer_id order by id")
    List<Task> findByEmployer_id(@Param("employer_id") Long id);

    @Query("select t from Task t where t.manager_id = :user_id or t.employer_id = :user_id order by id")
    List<Task> findByManager_idAndEmployer_id(@Param("user_id") Long id);

    @Query("select t from Task t " +
           "where t.status in ('CREATED', 'ONGOING') " +
           "and t.due_time between :fromDate and :toDate")
    List<Task> findByDueTimeInInterval(@Param("fromDate") Date from, @Param("toDate") Date to);

}
