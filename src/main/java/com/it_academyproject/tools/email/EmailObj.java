package com.it_academyproject.tools.email;

import javax.mail.Session;
import java.util.Properties;

public class EmailObj
{
	private String to;

	private String from;
	
	private Properties properties;
	
	private Session session;
	
	private String subject;
	
	private String content;

	// -------------------- -------------------- //
	
	private void setProperties()
	{
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "mail.virginiacampo.com");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.user", "itacademy@virginiacampo.com");
		properties.setProperty("mail.smtp.password", "itacademy");
		//properties.setProperty("mail.smtp.timeout", "itacademy");
		properties.put("mail.smtp.auth", "true");
	}

	public EmailObj()
	{
		setProperties();
		session = Session.getDefaultInstance(properties);
	}

	public EmailObj(String to, String from, String subject, String content)
	{
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.content = content;
		setProperties();
		session = Session.getDefaultInstance(properties);
	}

	// -------------------- -------------------- //
	
	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public Properties getProperties()
	{
		return properties;
	}

	public Session getSession()
	{
		return session;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

}