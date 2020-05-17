package com.it_academyproject.jwt_security.constants;

public final class SecurityConstants
{
	public static final String AUTH_LOGIN_URL = "/api/login";

	// Signing key for HS512 algorithm
	// You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
	// Note: You should not hardcode JWT signing key into your application code (we
	// will ignore that for now in the example).
	// You should use an environment variable or .properties file. Also, keys need
	// to have an appropriate length.
	// For example, HS512 algorithm needs a key with size at least 512 bytes.
	public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

	// JWT token defaults
	public static final String TOKEN_HEADER = "Authorization";
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String TOKEN_TYPE = "JWT";
	
	public static final String TOKEN_ISSUER = "secure-api";
	
	public static final String TOKEN_AUDIENCE = "secure-app";

	// -------------------- -------------------- //
	
	private SecurityConstants()
	{
		throw new IllegalStateException("Cannot create instance of static util class");
	}

}