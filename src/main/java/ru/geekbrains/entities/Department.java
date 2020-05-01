package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "departments")
public class Department {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "head_id")
    private int head_id;

    @Column (name = "title")
    private String title;

    @Transient
    private String dept_name;

    @Column (name = "up_department_id")
    private int up_department_id;

    @Transient
    private int level;

    @OneToMany (mappedBy = "department")
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "up_department_id")
    @OrderColumn
    private List<Department> children = new LinkedList<Department>();


}
