package ru.geekbrains.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entities.Department;
import ru.geekbrains.entities.DepartmentQ;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query(value ="WITH RECURSIVE Rec(id,  title, head_id, dept_name,up_department_id, Level) AS(   \n" +
            "\n" +
            "SELECT \n" +
            "\tid, title, head_id, cast(title as varchar(250)) AS  dept_name,up_department_id,1 as level\n" +
            "FROM departments\n" +
            "WHERE up_department_id=0\n" +
            "\n" +
            "UNION all\n" +
            "\n" +
            "SELECT D.id, D.title,d.head_id, cast(lpad(' ', 6*level) || D.title  as varchar(250)) as dept_name,d.up_department_id, Level+1 as level \n" +
            "FROM departments D \n" +
            "JOIN Rec R \n" +
            "ON (r.id = d.up_department_id))\n" +
            "SELECT * FROM Rec"
            , nativeQuery = true)
    List<String> findTree();
}
