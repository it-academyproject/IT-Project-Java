package com.it_academyproject.jwt_security.security;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@Service
@Configurable
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private WebApplicationContext applicationContext;

	private MyAppUserRepository myAppUserRepository;

	// -------------------- -------------------- //
	
	public MyUserDetailsService()
	{
	
	}

	@PostConstruct
	public void completeSetup()
	{
		try
		{
			if (myAppUserRepository == null)
			{
				myAppUserRepository = applicationContext.getBean(MyAppUserRepository.class);
			}
		} 
		catch (BeansException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email)
	{
		MyAppUser myAppUser = myAppUserRepository.findByEmail(email);
		if (myAppUser == null)
		{
			throw new UsernameNotFoundException(email);
		}
		return new MyUserPrincipal(myAppUser);
	}

}