package ru.geekbrains.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "notifications")
public class Notification {

    public enum Status {
        UNCHECKED, CHECKED
    }

    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "data")
    private String data;

    @Column (name = "date_create")
    private Date date_create;

    @Column (name = "task_id")
    private Long task_id;

    @Column (name = "project_id")
    private Long project_id;

    @Column (name = "receiver_id")
    private Long receiver_id;

    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
