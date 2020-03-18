package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "projects")

public class Project {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "title")
    private String title;

    @Column (name = "description")
    private String description;

    @Column (name = "manager_id")
    private Long manager_id;

    @ManyToMany
    @JoinTable (name = "users_projects",
            joinColumns = @JoinColumn (name = "project_id"),
            inverseJoinColumns = @JoinColumn (name = "user_id")
    )
    private List<User> users;
}
