package ru.geekbrains.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class DocFilter {
    private Long idObject;
    private String typeObject;
    private String name;
    private String description;
    @Id
    private Long id;

    public DocFilter(Long idObject, String typeObject){
        this.idObject=idObject;
        this.typeObject=typeObject;
    }


}
