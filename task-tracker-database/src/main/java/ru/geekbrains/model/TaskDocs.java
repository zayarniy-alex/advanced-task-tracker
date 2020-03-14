package ru.geekbrains.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task_docs")
public class TaskDocs implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name =" description")
    private String description;

    @Column(name = "data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "task_id" )
    private Tasks task;


    public TaskDocs() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
