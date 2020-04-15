package ru.geekbrains.utils;

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entities.Task;
import ru.geekbrains.repositories.specifications.TaskSpecifications;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Getter
public class TaskFilter {

    private Specification<Task> spec;
    private StringBuilder filterDefinition;

    public TaskFilter(Map<String, String> map) throws ParseException {

        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();

        Long employerId = 0L;
        Long managerId = 0L;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (map.containsKey("employer_id") && !map.get("employer_id").isEmpty()) {
            employerId = Long.parseLong(map.get("employer_id"));
            filterDefinition.append("&employer_id=").append(employerId);
        }

        if (map.containsKey("manager_id") && !map.get("manager_id").isEmpty()) {
            managerId = Long.parseLong(map.get("manager_id"));
            filterDefinition.append("&manager_id=").append(managerId);
        }

        if (map.containsKey("whois") && !map.get("whois").isEmpty()) {

            Integer whois = Integer.parseInt(map.get("whois"));
            if (whois == 1 && map.containsKey("employer_id") && !map.get("employer_id").isEmpty()) {
                    filterDefinition.append("&whois=").append(whois);
                    spec = spec.and(TaskSpecifications.employerIdEquals(employerId));
            } else if (whois == 2 && map.containsKey("manager_id") && !map.get("manager_id").isEmpty()) {
                    filterDefinition.append("&whois=").append(whois);
                    spec = spec.and(TaskSpecifications.managerIdEquals(managerId));
            } else {
                if (map.containsKey("manager_id") && !map.get("manager_id").isEmpty()
                        && map.containsKey("employer_id") && !map.get("employer_id").isEmpty()) {
                    spec = spec.and(TaskSpecifications.managerIdEquals(managerId).or(TaskSpecifications.employerIdEquals(employerId)));
                }
            }
        } else {
            if (map.containsKey("manager_id") && !map.get("manager_id").isEmpty()
                    && map.containsKey("employer_id") && !map.get("employer_id").isEmpty()) {
                spec = spec.and(TaskSpecifications.managerIdEquals(managerId).or(TaskSpecifications.employerIdEquals(employerId)));
            }
        }

        if (map.containsKey("urgency") && !map.get("urgency").isEmpty()) {
            spec = spec.and(TaskSpecifications.urgencyEquals(map.get("urgency")));
            filterDefinition.append("&urgency=").append(map.get("urgency"));
        }

        if (map.containsKey("status") && !map.get("status").isEmpty()) {
            spec = spec.and(TaskSpecifications.statusEquals(map.get("status")));
            filterDefinition.append("&status=").append(map.get("status"));
        }

        if (map.containsKey("project_id") && !map.get("project_id").isEmpty()) {
            Long projectId = Long.parseLong(map.get("project_id"));
            spec = spec.and(TaskSpecifications.projectIdEquals(projectId));
            filterDefinition.append("&project_id=").append(projectId);
        }

        if (map.containsKey("start_time") && !map.get("start_time").isEmpty()) {
            Date start_time = sdf.parse(map.get("start_time"));
            spec = spec.and(TaskSpecifications.startTimeEquals(start_time));
            filterDefinition.append("&start_time=").append(start_time);
        }

        if (map.containsKey("due_time") && !map.get("due_time").isEmpty()) {
            Date due_time = sdf.parse(map.get("due_time"));
            spec = spec.and(TaskSpecifications.dueTimeEquals(due_time));
            filterDefinition.append("&due_time=").append(due_time);
        }
    }

}
