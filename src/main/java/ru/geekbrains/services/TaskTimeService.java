package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.entities.TaskTime;
import ru.geekbrains.entities.TaskTimeFilter;
import ru.geekbrains.repositories.TasksTimeRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            taskTime.setTime_elapsed(
                    (TimeUnit.DAYS.convert(
                            (taskTime.getDate_finish().getTime() -
                            taskTime.getDate_start().getTime())
                            , TimeUnit.MILLISECONDS)+1)*8);
        }
        else {
            if (taskTime.getDate_start() == null)
                taskTime.setDate_start(new Date());

            Calendar cal = Calendar.getInstance();
            cal.setTime(taskTime.getDate_start());
            cal.add(Calendar.HOUR_OF_DAY, (int) taskTime.getTime_elapsed());
            cal.getTime();

            if (taskTime.getDate_finish() == null)
                taskTime.setDate_finish(cal.getTime());
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
            LocalDate date =  LocalDate.now().minusDays(30);
            localFilter.setDateStart(java.sql.Date.valueOf(date));

        }
        if (localFilter.getDateFinish()==null){
            LocalDate date =  LocalDate.now().plusDays(1);
            localFilter.setDateFinish(java.sql.Date.valueOf(date));
        }

        List<TaskTime> list=tasksTimeRepository.findByFilter(localFilter.getIdTask(),
                localFilter.getDateStart(),localFilter.getDateFinish());
        return list;

    }


}
