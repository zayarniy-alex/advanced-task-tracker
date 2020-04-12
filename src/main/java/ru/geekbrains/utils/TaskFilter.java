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

        // как объединить эти два условия - чтобы показывать и созданные юзером задачи, и назначенные ему?
        // сейчас - с условием or получается, что задача ИЛИ соответствует всем условиям фильтра ИЛИ назначена ему
        if (map.containsKey("manager_id") && !map.get("manager_id").isEmpty()
            && map.containsKey("employer_id") && !map.get("employer_id").isEmpty()) {

            Long managerId = Long.parseLong(map.get("manager_id"));
            spec = spec.and(TaskSpecifications.managerIdEquals(managerId));
            filterDefinition.append("&manager_id=").append(managerId);

            Long employerId = Long.parseLong(map.get("employer_id"));
            spec = spec.or(TaskSpecifications.employerIdEquals(employerId));
            filterDefinition.append("&employer_id=").append(employerId);
        }

//        if (map.containsKey("urgency") && !map.get("urgency").isEmpty()) {
//            spec = spec.and(TaskSpecifications.urgencyEquals(map.get("urgency").toString()));
//            filterDefinition.append("&urgency=").append(map.get("urgency"));
//        }
//
//        if (map.containsKey("status") && !map.get("status").isEmpty()) {
//            spec = spec.and(TaskSpecifications.statusEquals(map.get("status")));
//            filterDefinition.append("&status=").append(map.get("status"));
//        }

        if (map.containsKey("project_id") && !map.get("project_id").isEmpty()) {
            Long projectId = Long.parseLong(map.get("project_id"));
            spec = spec.and(TaskSpecifications.projectIdEquals(projectId));
            filterDefinition.append("&project_id=").append(projectId);
        }

    }
}
