package ru.geekbrains.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entities.Task;
import ru.geekbrains.repositories.specifications.TaskSpecifications;

import java.util.Map;

@Getter
public class TaskFilter {

    private Specification<Task> spec;
    private StringBuilder filterDefinition;

    public TaskFilter(Map<String, String> map) {

        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();

        if (map.containsKey("manager_id") && !map.get("manager_id").isEmpty()
            && map.containsKey("employer_id") && !map.get("employer_id").isEmpty()) {
            Long managerId = Long.parseLong(map.get("manager_id"));
            spec = spec.and(TaskSpecifications.managerIdEquals(managerId));

            Long employerId = Long.parseLong(map.get("employer_id"));
            spec = spec.or(TaskSpecifications.employerIdEquals(employerId));
            filterDefinition.append("&employer_id=").append(employerId);
        }

        if (map.containsKey("project_id") && !map.get("project_id").isEmpty()) {
            Long projectId = Long.parseLong(map.get("project_id"));
            spec = spec.and(TaskSpecifications.projectIdEquals(projectId));
            filterDefinition.append("&project_id=").append(projectId);
        }

    }
}
