package com.it_academyproject.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.exceptions.EmptyFieldException;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyContentUtil
{

    private MyAppUserRepository myAppUserRepository;
    public DummyContentUtil (MyAppUserRepository myAppUserRepository)
    {
        this.myAppUserRepository = myAppUserRepository;
    }
    public List<MyAppUser> generateDummyUsers()
    {
        List<MyAppUser> users = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            users.add(new Student("vickycampo@gmail.com", passwordEncoder.encode("123456")));


            MyAppUser myAppUser;
            for (int i = 0; i < users.size() ; i++)
            {
                myAppUser = myAppUserRepository.findByEmail(users.get(i).getEmail());
                if (myAppUser != null)
                {
                    myAppUser.setPassword(users.get(i).getPassword());
                    myAppUserRepository.save(myAppUser);
                }
                else
                {
                    myAppUserRepository.save(users.get(i));
                }


            }
        }
        catch (EmptyFieldException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public static Collection<GrantedAuthority> getAuthorities()
    {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            public String getAuthority() {
                return "ROLE_USER";
            }
        };
        grantedAuthorities.add(grantedAuthority);
        return grantedAuthorities;
    }

}
