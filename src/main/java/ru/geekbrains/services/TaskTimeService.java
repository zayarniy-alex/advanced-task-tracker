package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.TaskTime;
import ru.geekbrains.entities.TaskTimeFilter;
import ru.geekbrains.repositories.TasksTimeRepository;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class TaskTimeService {

    private TaskTimeService taskTimeService;
    private TasksTimeRepository tasksTimeRepository;
    private UserService userService;


    @Autowired
    public void setTasksTimeRepository(TasksTimeRepository tasksTimeRepository) {
        this.tasksTimeRepository=tasksTimeRepository;
    }

    @Autowired
    public void setTaskTimeService(TaskTimeService taskTimeService) {
        this.taskTimeService=taskTimeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public TaskTime save(TaskTime taskTime, Principal principal) {
        if (taskTime.getTime_elapsed()==0.0&&taskTime.getDate_start()!=null
           &&taskTime.getDate_finish()!=null
        ){
            taskTime.setTime_elapsed((Duration.between(taskTime.getDate_start(),
                    taskTime.getDate_finish()).toDays()+1)*8);

        }
        else {
            if (taskTime.getDate_start() == null)
                taskTime.setDate_start(LocalDateTime.now());

            if (taskTime.getDate_finish() == null)
            taskTime.setDate_finish(taskTime.getDate_start().plusHours(
                    (long)
                    taskTime.getTime_elapsed()));
        }

        taskTime.setUser_id(userService.getUser(principal.getName()).getId());
        return tasksTimeRepository.save(taskTime);
    }

    public List<TaskTime> getFilter(TaskTimeFilter taskTimeFilter){
        TaskTimeFilter localFilter=new TaskTimeFilter();
        localFilter=taskTimeFilter;

        if (localFilter.getUserId()==null)
            localFilter.setUserId(0L);

        if (localFilter.getDateStart()==null){
            localFilter.setDateStart(LocalDateTime.now().minusDays(30).truncatedTo(MINUTES));
        }

        System.out.println(localFilter.getDateStart());

        if (localFilter.getDateFinish()==null){
            localFilter.setDateFinish(LocalDateTime.now().plusDays(1).truncatedTo(MINUTES));
        }

        List<TaskTime> list=tasksTimeRepository.findByFilter(localFilter.getIdTask(),
                localFilter.getDateStart(),localFilter.getDateFinish());
        return list;

    }


}
