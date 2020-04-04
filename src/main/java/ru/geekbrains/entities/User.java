package ru.geekbrains.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static java.util.Collections.singletonList;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "login")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "first_name")
    private String firstname;

    @Column (name = "last_name")
    private String lastname;

    @Column (name = "email")
    private String email;

//    @Column (name = "department_id")
////    private Long department_id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column (name = "position")
    private String position;

    @ManyToMany
    @JoinTable (name = "users_roles",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )
    private List<Role> roles;

    @ManyToMany
    @JoinTable (name = "users_projects",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "project_id")
    )
    private List<Project> projects;


    public void setRole(Role role) {
        setRoles(singletonList(role));
    }

}