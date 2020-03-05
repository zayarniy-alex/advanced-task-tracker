package ru.geekbrains;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource("classpath:private.properties")
public class TaskTrackerApplication
{

  public static void main(String[] args)
  {
	SpringApplication.run(TaskTrackerApplication.class, args);
  }

}
