package com.it_academyproject.repositories;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.it_academyproject.ApplicationConfig;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsExerciseNotTurnedInI;
import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.StatusExercise;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserExerciseRepository extends JpaRepository <UserExercise, Integer>
{
    UserExercise findOneByUserStudentAndExercise(Student user , Exercise exercise);
    List<UserExercise> findByUserStudent(Student user );
    List<UserExercise> findAllByExercise(Exercise exercise);
	List<UserExercise> findByExerciseId(int id);

    @JsonPropertyOrder({"first_name", "last_name", "email", "daysLastTurnedIn"})
    @Query(value = "SELECT u.id, u.first_name, u.last_name, u.email, ue.exercise_id, ue.status_id, ue.date_status, " +
            "			timestampdiff(DAY, ue.date_status, NOW()) AS daysLastTurnedIn " +
            "		FROM user_exercise ue, users u " +
            "		WHERE ue.student_id = u.id " +
            "			AND timestampdiff(DAY, ue.date_status, NOW()) > :notTurnedInDays " +
            "			AND ue.status_id NOT IN(:exerciseStatusExclude) " +
            "       GROUP BY u.id " +
            "       ORDER BY u.last_name ASC" , nativeQuery = true)
    List<DTOStudentsExerciseNotTurnedInI> getNotifyNotTurnedInExercises(Integer notTurnedInDays, List<Integer> exerciseStatusExclude);

}