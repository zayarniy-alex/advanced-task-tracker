package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "task_history")
public class TaskHistory {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "task_id")
    private Long task_id;

    @Column (name = "change_date")
    private Date change_date;

    @Column (name = "description")
    private String description;

    @Column (name = "user_id")
    private Long user_id;
}
