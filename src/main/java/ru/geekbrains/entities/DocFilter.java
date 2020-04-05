package ru.geekbrains.entities;

public class DocFilter {
    private Long idObject;
    private String typeObject;
    private String name;
    private String description;
    private Long id;

    public DocFilter(Long idObject, String typeObject){
        this.idObject=idObject;
        this.typeObject=typeObject;
    }


    public Long getIdObject() {
        return idObject;
    }

    public void setIdObject(Long idObject) {
        this.idObject = idObject;
    }

    public String getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(String typeObject) {
        this.typeObject = typeObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
