/*
package com.it_academyproject.jwt_security.util;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService
{

	private static String protocol = "smtp";

	private String host = "mail.virginiacampo.com";

	private int port = 587;

	private boolean auth = true;

	private boolean starttls = true;

	private int timeout = 5000;

	private String from = "itacademy@virginiacampo.com";

	private String username = "itacademy@virginiacampo.com";

	private String password = "itacademy";

	private boolean send = true;

	@Bean
	public JavaMailenderImpl mailSender()
	{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// set mail sender configuration
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setProtocol(protocol);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		// create java mail properties
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", auth);
		mailProperties.put("mail.smtp.starttls.enable", starttls);
		mailProperties.put("mail.smtp.timeout", timeout);
		mailProperties.put("mail.smtp.connectiontimeout", timeout);

		// set java mail properties
		mailSender.setJavaMailProperties(mailProperties);

		return mailSender;
	}

	public JavaMailSender emailSender = mailSender();

	@Override
	public void sendSimpleMessage(String to, String subject, String text)
	{
		try
		{
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			emailSender.send(message);
		} catch (MailException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
	{

		try
		{
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);
			emailSender.send(message);
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}
*/