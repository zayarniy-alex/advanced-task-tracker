package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Data
@NoArgsConstructor
public class ProjectFilter {
        private String title;
        private Long managerId;
        private Project.Status status;
        @Id
        private Long id;

        public boolean isNull(){
            return (title==null&&managerId==null&&status==null);
        }
}
