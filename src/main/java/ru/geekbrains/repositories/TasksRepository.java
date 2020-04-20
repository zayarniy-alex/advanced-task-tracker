package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

}
