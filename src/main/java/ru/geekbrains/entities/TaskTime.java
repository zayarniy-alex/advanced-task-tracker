package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "task_time")

public class TaskTime {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "task_id")
    private Long task_id;

    @Column (name = "user_id")
    private Long user_id;

    @Column (name = "date_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_start;

    @Column (name = "date_finish")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  date_finish;

    @Column (name = "time_elapsed")
    private double time_elapsed;

    @OneToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;
}
