package com.it_academyproject.repositories;

import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserExerciseRepository extends JpaRepository <UserExercise, Integer>
{
    UserExercise findOneByUserStudentAndExercise(Student user, Exercise exercise);
    List<UserExercise> findByUserStudent(Student user);
    List<UserExercise> findAllByExercise(Exercise exercise);
    
    
    /////////////////////
    
    @Query("SELECT u FROM UserExercise u WHERE student_id IS NOT NULL AND status_exercise_id BETWEEN 3 AND 5 GROUP BY student_id ORDER BY date_status ASC")
	List<UserExercise> findAllByRecentExerciseDelivered(Exercise exercises);

    /////////////////////

}