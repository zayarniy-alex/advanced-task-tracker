package ru.geekbrains.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.entities.Task;

public class TaskSpecifications {

    public static Specification<Task> managerIdEquals(Long managerId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("manager_id"), managerId);
    }

    public static Specification<Task> employerIdEquals(Long employerId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("employer_id"), employerId);
    }

    public static Specification<Task> urgencyEquals(String urgency) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("urgency"), urgency);
    }

    // с enum возникают такие ошибки
    // Parameter value [ONGOING] did not match expected type [ru.geekbrains.entities.Task$Status (n/a)];
    // nested exception is java.lang.IllegalArgumentException:
    // Parameter value [ONGOING] did not match expected type [ru.geekbrains.entities.Task$Status (n/a)]
    public static Specification<Task> statusEquals(String status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> projectIdEquals(Long projectId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("project_id"), projectId);
    }
}
