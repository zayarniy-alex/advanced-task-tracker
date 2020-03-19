package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
