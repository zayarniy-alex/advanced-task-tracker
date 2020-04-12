package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class CommentFilter {
    private Long idTask;
    private String data=null;
    private Long idAuthor;
    private String nameAuthor;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTill;
    @Id
    private Long id;

    public CommentFilter(Long idTask, String data, Long idAuthor, Date dateFrom, Date dateTill){
        this.idTask=idTask;
        this.data=data;
        this.idAuthor=idAuthor;
        this.dateFrom=dateFrom;
        this.dateTill=dateTill;
    }


}
