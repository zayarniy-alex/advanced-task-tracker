package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table (name = "documents")
public class Document {
    @Id
    @GeneratedValue (strategy = IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "title")
    private String title;

    @Column (name = "description")
    private String description;

    @Column (name = "data")
    private String data;

    @Column (name = "object_id")
    private Long object_id;

    @Column (name = "object_type")
    private String object_type;
}
