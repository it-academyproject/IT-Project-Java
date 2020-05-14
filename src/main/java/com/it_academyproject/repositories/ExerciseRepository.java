package com.it_academyproject.repositories;


import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>
{
    List<Exercise> findAllByNameAndItinerary (String name , Itinerary itinerary);
    Exercise findOneById (Integer exerciseId );
}

