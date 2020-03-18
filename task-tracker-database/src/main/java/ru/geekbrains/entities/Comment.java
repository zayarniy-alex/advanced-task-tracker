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
    public enum CommentType {
        SIMPLE, PERSONAL, GENERAL
    }

    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "data")
    private String data;

    @Column (name = "object_type")
    private String object_type;

    @Column (name = "object_id")
    private Long object_id;

    @Column (name = "author_id")
    private Long author_id;

    @Column (name = "date_create")
    private Date date_create;

    @Column (name = "comment_type")
    private CommentType comment_type;
}
