package com.it_academyproject.jwt_security.security;

import com.it_academyproject.domains.MyAppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyUserPrincipal implements UserDetails
{
	private static final long serialVersionUID = 1L;

	private MyAppUser myAppUser;

	// -------------------- -------------------- //
	
	public MyUserPrincipal(MyAppUser user)
	{
		this.myAppUser = user;
	}

	// -------------------- -------------------- //

	@Override
	public String getUsername()
	{
		return myAppUser.getEmail();
	}

	@Override
	public String getPassword()
	{
		return myAppUser.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return myAppUser.isEnabled();
	}

	public MyAppUser getUser()
	{
		return myAppUser;
	}

}