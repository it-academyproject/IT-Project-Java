package com.it_academyproject.tools.email;

// File Name SendHTMLEmail.java

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailUtil
{
	private EmailObj emailObj;

	// -------------------- -------------------- //
	
	public EmailUtil()
	{
		emailObj = new EmailObj();
	}

	public EmailUtil(EmailObj emailObj)
	{
		this.emailObj = emailObj;
	}

	public EmailUtil(String to, String from, String subject, String content)
	{
		emailObj = new EmailObj(to, from, subject, content);
	}

	public void setEmailObj(EmailObj emailObj)
	{
		this.emailObj = emailObj;
	}

	public boolean sendHTMLEmail() throws Exception
	{
		try
		{
			// Create a default MimeMessage object.
			SmtpAuthenticator authentication = new SmtpAuthenticator();
			authentication.setPassword(emailObj.getProperties().getProperty("mail.smtp.password"));
			authentication.setUsername(emailObj.getProperties().getProperty("mail.smtp.user"));
			MimeMessage message = new MimeMessage(emailObj.getSession().getInstance(emailObj.getProperties(), authentication));
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(emailObj.getFrom()));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailObj.getTo()));
			// Set Subject: header field
			message.setSubject(emailObj.getSubject());
			// Send the actual HTML message, as big as you like
			message.setContent(emailObj.getContent(), "text/html");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return (true);
		} 
		catch (MessagingException mex)
		{
			mex.printStackTrace();
			throw (new Exception(mex.getMessage()));
		}
	}

	public boolean sendFileEmail(String fileName) throws Exception
	{
		try
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(emailObj.getSession().getDefaultInstance(emailObj.getProperties()));
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(emailObj.getFrom()));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailObj.getTo()));
			// Set Subject: header field
			message.setSubject(emailObj.getSubject());
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setText(emailObj.getContent());
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = fileName;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			// Send the complete message parts
			message.setContent(multipart);
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return true;
		} 
		catch (MessagingException mex)
		{
			mex.printStackTrace();
			throw (new Exception(mex.getMessage()));
		}
	}

	public boolean sendEmail() throws Exception
	{
		try
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(emailObj.getSession());
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(emailObj.getFrom()));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailObj.getTo()));
			// Set Subject: header field
			message.setSubject(emailObj.getSubject());
			// Now set the actual message
			message.setText(emailObj.getContent());
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			return (true);
		} 
		catch (MessagingException mex)
		{
			mex.printStackTrace();
			throw (new Exception(mex.getMessage()));
		}
	}

}