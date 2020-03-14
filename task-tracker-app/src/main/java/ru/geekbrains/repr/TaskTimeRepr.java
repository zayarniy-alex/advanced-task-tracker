package ru.geekbrains.repr;


import ru.geekbrains.model.TaskTime;

import java.io.Serializable;
import java.sql.Date;

public class TaskTimeRepr implements Serializable {

    private Long id;

    private Date dateStart;

    private Date dateFinish;

    private boolean timeElapsed;

    private Tasks tasks;

    public TaskTimeRepr() {
    }

    public TaskTimeRepr(TaskTime taskTime) {
        this.id = taskTime.getId();
        this.dateStart = taskTime.getDateStart();
        this.dateFinish = taskTime.getDateFinish();
        this.timeElapsed = taskTime.isTimeElapsed();
        this.tasks = taskTime.getTasks();
    }

    @Override
    public String toString() {
        return "TaskTimeRepr{" +
                "id=" + id +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", timeElapsed=" + timeElapsed +
                ", tasks=" + tasks +
                '}';
    }

}
