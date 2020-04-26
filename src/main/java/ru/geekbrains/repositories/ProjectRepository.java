package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select d " +
            "from Project d " +
            "where (d.manager_id=:manager_id or :manager_id is null or :manager_id=0) and " +
            "(d.title like :title or :title is null) and " +
            "(d.status=:status or :status is null)" )
    List<Project> findProjectByFilter
            (@Param("manager_id") Long manager_id,
             @Param("title") String title,
             @Param("status") Project.Status status
            );

}
