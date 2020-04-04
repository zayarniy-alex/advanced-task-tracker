package ru.geekbrains;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@PropertySource ("file:src/main/resources/private.properties")
public class TaskTrackerApplication {
    private static final Class<TaskTrackerApplication> APPLICATION_CLASS =
            TaskTrackerApplication.class;

    public static void main(String[] args) {
        run(APPLICATION_CLASS, args);
//	System.out.println(PasswordEncoderGenerator.generate("123"));
    }
}
