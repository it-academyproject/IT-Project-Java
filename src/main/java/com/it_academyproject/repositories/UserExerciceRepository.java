package com.it_academyproject.repositories;

import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExerciceRepository extends JpaRepository <UserExercice , Integer>
{
    UserExercice findOneByUserStudentAndExercise(MyAppUser user , Exercise exercise);
    List<UserExercice> findByUserStudent(MyAppUser user );

    //List<UserExercice> findBy
}