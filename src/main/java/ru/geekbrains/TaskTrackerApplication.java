package ru.geekbrains;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static org.springframework.boot.SpringApplication.run;
import static ru.geekbrains.config.MailSenderConfig.*;


@SpringBootApplication
@PropertySource ("file:src/main/resources/private.properties")
public class TaskTrackerApplication {
    private static final Class<TaskTrackerApplication> APPLICATION_CLASS =
            TaskTrackerApplication.class;

    public static void main(String[] args) {
        run(APPLICATION_CLASS, args);
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(MAIL_HOST);
        sender.setPort(MAIL_PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return sender;
    }

}
