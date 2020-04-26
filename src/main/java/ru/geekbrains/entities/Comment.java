package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "comments")
public class Comment {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "data")
    private String data;

    @Column (name = "task_id")
    private Long task_id;

    @Column (name = "author_id")
    private Long author_id;

    @Column (name = "date_create")
    private Date date_create;

    @OneToOne
    @JoinColumn(name = "author_id", insertable=false, updatable=false)
    private User user;

}
