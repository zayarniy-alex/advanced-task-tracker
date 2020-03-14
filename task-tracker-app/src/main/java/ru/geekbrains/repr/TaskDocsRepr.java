package ru.geekbrains.repr;

import ru.geekbrains.model.TaskDocs;

import java.io.Serializable;
import java.util.Arrays;

public class TaskDocsRepr implements Serializable {

    private Long id;


    private String title;


    private String description;


    private byte[] data;

    public TaskDocsRepr() {
    }

    public TaskDocsRepr(TaskDocs taskDocs) {
        this.id = taskDocs.getId();
        this.title = taskDocs.getTitle();
        this.description = taskDocs.getDescription();
        this.data = taskDocs.getData();
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

    @Override
    public String toString() {
        return "TaskDocsRepr{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
