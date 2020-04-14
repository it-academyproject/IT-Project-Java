package com.it_academyproject.repositories;

import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExerciceRepository extends JpaRepository <UserExercice , Integer>
{
    UserExercice findOneByUserStudentAndExercice(MyAppUser user , Exercice exercice );
    
    UserExercice findOneByUserStudent(MyAppUser user );
    
//    List<UserExercice> findOneByItineraryAndExercice(Itinerary itinerary , Exercice exercice);
    
    List<UserExercice> findByUserStudent(MyAppUser user );
    
//    UserExercice findByExercise(Exercice exercice);
    

    //List<UserExercice> findBy
}