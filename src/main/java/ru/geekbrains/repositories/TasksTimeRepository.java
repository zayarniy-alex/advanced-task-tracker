package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.TaskTime;

import java.util.Date;
import java.util.List;

@Repository
public interface TasksTimeRepository extends JpaRepository<TaskTime, Long> {

    @Query("select t from TaskTime t where t.task_id = :task_id " +
            " and (t.date_start between :date_start and :date_finish)" +
            " order by t.date_start desc")
    List<TaskTime> findByFilter(@Param("task_id") Long id,
                                @Param("date_start") Date dateStart,
                                @Param("date_finish") Date dateFinish

    );

}
