package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.Project;
import ru.geekbrains.entities.ProjectFilter;
import ru.geekbrains.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).get();
    }

    public List<Project> findProjectFilter(Long manager_id,
                                           String title,
                                           Project.Status status) {

        ProjectFilter localFilter=new ProjectFilter();
        localFilter.setManagerId(manager_id);
        localFilter.setStatus(status);
        localFilter.setTitle(title);

        if (localFilter.getTitle().equals(""))
            localFilter.setTitle("%");

        return projectRepository.findProjectByFilter(localFilter.getManagerId(),localFilter.getTitle(),localFilter.getStatus());
    }

}
