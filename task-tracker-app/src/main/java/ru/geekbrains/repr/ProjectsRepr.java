package ru.geekbrains.repr;

import ru.geekbrains.model.Projects;

import java.io.Serializable;
import java.util.List;

public class ProjectsRepr implements Serializable {
    private Long id;

    private String title;

    private String description;

    private List<Users> users;

    public ProjectsRepr() {
    }

    public ProjectsRepr(Projects projects) {
        this.id = projects.getId();
        this.title = projects.getTitle();
        this.description = projects.getDescription();
        this.users = projects.getUsers();
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

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ProjectsRepr{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
