package com.it_academyproject.tools.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator
{
	private String username;

	private String password;

	// -------------------- -------------------- //
	
	public SmtpAuthenticator()
	{

	}

	// -------------------- -------------------- //
	
	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication()
	{
		if ((username != null) && (username.length() > 0) && (password != null) && (password.length() > 0))
		{
			return new PasswordAuthentication(username, password);
		}
		return null;
	}

}