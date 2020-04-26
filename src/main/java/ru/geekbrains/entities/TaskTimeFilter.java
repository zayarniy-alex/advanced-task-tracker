package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class TaskTimeFilter {
        private Long userId;
        private Long idTask;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateStart;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date dateFinish;
        @Id
        private Long id;

        public boolean isNull(){
            if (userId==null&&dateStart==null&&dateFinish==null)
                return true;
            else
                return false;
        }

        public TaskTimeFilter(Long idTask, Long userId, Date dateStart, Date dateFinish){
            this.idTask=idTask;
            this.userId=userId;
            this.dateStart=dateStart;
            this.dateFinish=dateFinish;
        }
    }
