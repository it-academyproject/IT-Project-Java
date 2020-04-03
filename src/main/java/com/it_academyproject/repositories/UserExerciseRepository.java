package com.it_academyproject.repositories;

import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExerciseRepository extends JpaRepository <UserExercise, Integer>
{
    UserExercise findOneByUserStudentAndExercise(Student user , Exercise exercise);
    List<UserExercise> findByUserStudent(Student user );

    //List<UserExercise> findBy
}