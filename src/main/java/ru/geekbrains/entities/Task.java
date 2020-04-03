package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
    private String title;

    @Column (name = "description")
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
    private Long project_id;

    @Column (name = "plan_time")
    private Long plan_time;
}
