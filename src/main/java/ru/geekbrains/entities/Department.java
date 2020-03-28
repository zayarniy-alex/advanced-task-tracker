package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Long id;

    @Column (name = "title")
    private String title;

    @Column (name = "head_id")
    private Long head_id;

    @Column (name = "up_department_id")
    private Long up_department_id;
}
