package com.it_academyproject.repositories;

import com.it_academyproject.domains.StatusExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusExerciseRepository extends JpaRepository<StatusExercise, Integer>
{
        StatusExercise findOneById(Integer id );
        StatusExercise findByName(String name);
}
