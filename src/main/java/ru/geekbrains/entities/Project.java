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
    public enum Status {
        CREATED("Создан"), ONGOING("В работе"),
        COMPLETE("Завершен"), ARCHIVE("В архиве");
        private String code;

        private Status(String code) {
            this.code = code;
        }



        public String getCode() {
            return code;
        }
    }


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

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name = "manager_id", insertable=false, updatable=false)
    private User user;

    @OneToMany(mappedBy = "project_id")
    private List<Task> tasks;

    @ManyToMany
    @JoinTable (name = "users_projects",
            joinColumns = @JoinColumn (name = "project_id"),
            inverseJoinColumns = @JoinColumn (name = "user_id")
    )
    private List<User> users;
}
