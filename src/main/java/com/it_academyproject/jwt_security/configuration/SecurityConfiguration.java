package com.it_academyproject.jwt_security.configuration;

import com.it_academyproject.jwt_security.security.AuthenticationSuccessHandlerImpl;
import com.it_academyproject.jwt_security.security.JwtAuthenticationFilter;
import com.it_academyproject.jwt_security.security.JwtAuthorizationFilter;
import com.it_academyproject.jwt_security.security.MyUserDetailsService;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan("com.it_academyproject.jwt_security.security")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private WebApplicationContext applicationContext;
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MyAppUserRepository myAppUserRepository;
    
    

    @Autowired
    private AuthenticationSuccessHandlerImpl successHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void completeSetup ()
    {
        try
        {
            userDetailsService = applicationContext.getBean( MyUserDetailsService.class );
        }
        catch (BeansException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure ( final AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.userDetailsService( userDetailsService )
                .passwordEncoder( encoder () )
                .and()
                .authenticationProvider( authenticationProvider() )
                .jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enabled from users where email=?")
                .authoritiesByUsernameQuery("select email, 'ROLE_USER' from users where email=?");
    }

    @Override
    public void configure (WebSecurity web) throws Exception
    {
    //Original web ignoring. Expose only /public for data importer, and get/save pass
//    	web.ignoring()
//                .antMatchers("/api/public/**" , "/api/get-reset-email/**" , "/api/save-new-password/**");
    
    //B-17 task. Add to web.ignoring controllers used during development to avoid asking for a Token during dev
    	//Add endpoints when new controller is added in the API
    	web.ignoring()
<<<<<<< HEAD
        .antMatchers("/api/test/**","/api/public/**" , "/api/get-reset-email/**" , "/api/save-new-password/**" , "/api/students/**" , "/api/statistics/**" , "/api/userExercise/**","/api/iterations/**", "/api/projects/**", "/api/itineraries/**", "/api/useriteration/**", "/api/projectitinerary/**", "/api/absences/**");
=======
        .antMatchers("/api/test/**","/api/public/**" , "/api/get-reset-email/**" , "/api/save-new-password/**" , "/api/students/**" , "/api/statistics/**" , "/api/exercises/**","/api/iterations/**", "/api/projects/**", "/api/itineraries/**", "/api/useriteration/**", "/api/projectitinerary/**");
>>>>>>> fdccecc10e41a1ed16a3006e79e998737c504598
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
            http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                /*.antMatchers("/api/public/**").anonymous()*/
                .antMatchers("/api/**").authenticated()
                .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager() , myAppUserRepository))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider ()
    {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService );
        authProvider.setPasswordEncoder( encoder() );
        return authProvider;
    }
    @Bean
    public PasswordEncoder encoder()
    {

        //return passwordEncoder;
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder(11);
    }
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension()
    {
        return new SecurityEvaluationContextExtension();
    }
    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER");
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}

