package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "tasks")
public class Task {
    public enum Status {
        CREATED, ONGOING, COMPLETE, ARCHIVE
    }

    public enum Urgency {
        HIGH, AVERAGE, LOW
    }

    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "title")
    @NotNull(message = "Поле должно быть заполнено")
    @Size(min = 3, message = "Название должно быть длиннее 2 символов")
    private String title;

    @Column (name = "description")
    @NotNull(message = "Обязательное поле")
    @Size(min = 7, message = "Описание должно быть длиннее 7 символов")
    private String description;

    @Column (name = "manager_id")
    private Long manager_id;

    @Column (name = "employer_id")
    private Long employer_id;

    @Column (name = "start_time")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_time;

    @Column (name = "due_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Обязательное поле")
    private Date due_time;

    @Column (name = "urgency")
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column (name = "progress")
    private float progress;

    @Column (name = "project_id")
    @NotNull(message = "Обязательное поле")
    private Long project_id;

    @Column (name = "plan_time")
    @NotNull(message = "Обязательное поле")
    private Long plan_time;

    @ManyToOne
    @JoinColumn(name="project_id", insertable=false, updatable=false)
    private Project project;

}
