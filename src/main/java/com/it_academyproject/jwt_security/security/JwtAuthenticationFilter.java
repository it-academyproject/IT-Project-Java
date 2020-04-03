package com.it_academyproject.jwt_security.security;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.exceptions.EmptyFieldException;
import com.it_academyproject.exceptions.UserNotEnabled;
import com.it_academyproject.exceptions.WrongEmailPassword;
import com.it_academyproject.jwt_security.constants.SecurityConstants;
import com.it_academyproject.repositories.MyAppUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configurable
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private MyAppUserRepository myAppUserRepository;

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager , MyAppUserRepository myAppUserRepository)
    {
        this.authenticationManager = authenticationManager;
        this.myAppUserRepository = myAppUserRepository;
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }
	//B-27 Task: Update last time an user do login.
    public MyAppUser updateLastLogin(MyAppUser myAppUser) {

        if(myAppUserRepository.existsById(myAppUser.getId())) {
            MyAppUser user = myAppUserRepository.findOneById(myAppUser.getId());
            java.util.Date date = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            user.setLastLogin(timestamp);
            myAppUserRepository.save(user);
            return user;
        }else {return null;}
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {
        LoginData loginData = new LoginData();
        try
        {
            String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            JSONObject  loginDataJson = new JSONObject ( test );

            if ( (loginDataJson.has("email")) &&
                    (loginDataJson.has("password")) &&
                    (! loginDataJson.getString("email").equals("") ) &&
                    (! loginDataJson.getString("password").equals("") )
            )
            {
                loginData.setEmail(loginDataJson.getString("email"));
                loginData.setPassword(loginDataJson.get("password").toString());

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                MyAppUser myAppUser = myAppUserRepository.findByEmail(loginData.getEmail());

                if (myAppUser == null) throw new UserNotEnabled(loginData.getEmail());

                if ( passwordEncoder.matches(loginData.getPassword() , myAppUser.getPassword() ))
                {
                    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
                    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword(), grantedAuthorityList );


                    try
                    {
						//B27 Task: When authentication is succeed, date of last login is updated calling: 
                    	updateLastLogin(myAppUser);
                        return authenticationManager.authenticate(authenticationToken);
                    }
                    catch ( AuthenticationException e )
                    {
                        e.printStackTrace();
                        throw ( new UserNotEnabled(loginData.getEmail()));
                    }
                }
                else
                {
                    //double check on the user and password.
                    throw ( new WrongEmailPassword() );
                }
            }
            else if ( !(loginDataJson.has("email")) || (loginDataJson.getString("email").equals("")))
            {
                throw (new EmptyFieldException("email"));
            }
            else if ( !(loginDataJson.has("password")) || (loginDataJson.getString("password").equals("")))
            {
                throw (new EmptyFieldException("password"));
            }

        }
        catch (WrongEmailPassword e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.write(response.getWriter());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (UserNotEnabled e )
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.write(response.getWriter());
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        catch (EmptyFieldException e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                JSONObject json = new JSONObject();
                json.put("success", false);
                json.write(response.getWriter());

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return null;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        UserDetails userDetails = ((UserDetails) authentication.getPrincipal());

        Object roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + (8640000)))
                .claim("rol", roles)
                .compact();
        System.out.println("The expiration date of the token is: " + (new Date(System.currentTimeMillis() + (8640000)).toString()));

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        try {
            JSONObject json = new JSONObject();
            json.put("success", true);
            json.put("token", token);
            json.write(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

