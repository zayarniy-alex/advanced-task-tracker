package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class TaskTimeFilter {
        private Long userId;
        private Long idTask;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime dateStart;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime dateFinish;
        @Id
        private Long id;

        public boolean isNull(){
            return(userId==null&&dateStart==null&&dateFinish==null);
        }

        public TaskTimeFilter(Long idTask, Long userId, LocalDateTime dateStart, LocalDateTime dateFinish){
            this.idTask=idTask;
            this.userId=userId;
            this.dateStart=dateStart;
            this.dateFinish=dateFinish;
        }
    }
