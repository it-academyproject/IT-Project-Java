package com.it_academyproject.repositories;


import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Integer>
{
    List<Exercice> findAllByNameAndItinerary (String name , Itinerary itinerary);
    Exercice findOneById ( Integer exerciseId );
    
//    MyAppUser findUserByExercise(Exercice exercise);
}

