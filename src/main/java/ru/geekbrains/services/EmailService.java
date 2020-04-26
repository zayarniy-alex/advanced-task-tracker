package ru.geekbrains.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class EmailService
{

  private JavaMailSender mailSender;
  private static final String MSG_ENCODING = "utf-8";
  private static final String HTML_MSG_TYPE = "text/html; charset=" + MSG_ENCODING;


  @Autowired
  public void setMailSender(JavaMailSender sender)
  {
	mailSender = sender;
  }


  public void sendSimpleMessage(String to, String subject, String text)
  {
	SimpleMailMessage msg = new SimpleMailMessage();
	msg.setTo(to);
	msg.setSubject(subject);
	msg.setText(text);
	mailSender.send(msg);
  }


  public void sendHTMLmessage(String to, String subject, String htmlMsg)
  throws MessagingException
  {
	MimeMessage msg = mailSender.createMimeMessage();

	boolean multipart = true;
	MimeMessageHelper helper = new MimeMessageHelper(msg, multipart, MSG_ENCODING);
	helper.setTo(to);
	helper.setSubject(subject);
	msg.setContent(htmlMsg, HTML_MSG_TYPE);

	mailSender.send(msg);
  }


  public void sendMessageWithFiles(String to, String subject, String text, Path... pathes)
  throws MessagingException
  {
	MimeMessage msg = mailSender.createMimeMessage();

	MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	helper.setTo(to);
	helper.setSubject(subject);
	helper.setText(text);

	List<FileSystemResource> files =
			Arrays.stream(pathes)
				  .map(FileSystemResource::new)
				  .collect(toList());

	for (FileSystemResource file : files)
	{
	  String name = file.getFilename();
	  helper.addAttachment(name, file);
	}

	mailSender.send(msg);
  }


}