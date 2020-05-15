package com.it_academyproject.repositories;

import com.it_academyproject.domains.StatusExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusExerciseRepository extends JpaRepository<StatusExercise, Integer>
{
        StatusExercise findOneById(Integer id );
}
