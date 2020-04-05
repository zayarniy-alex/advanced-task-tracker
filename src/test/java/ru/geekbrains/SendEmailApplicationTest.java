package ru.geekbrains;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.services.EmailService;


@SpringBootTest
class SendEmailApplicationTest
{

  @Autowired
  private EmailService emailService;


  @Test
  void testEmail()
  {
	emailService.sendSimpleMessage("artur-java-dev@mail.ru", "Test subject", "Test mail");
  }

}