package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
