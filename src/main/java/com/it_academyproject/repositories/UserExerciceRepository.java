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
    
    UserExercice findOneByUserStudent(String id);
    
//    List<UserExercice> findOneByItineraryAndExercice(Itinerary itinerary , Exercice exercice);
    
    List<UserExercice> findByUserStudent(MyAppUser user );
<<<<<<< HEAD
    
//    UserExercice findByExercise(Exercice exercice);
    
=======
	List<UserExercice> findAllByExercice(Exercice exercise);
>>>>>>> fdccecc10e41a1ed16a3006e79e998737c504598

    //List<UserExercice> findBy
}
