package com.it_academyproject.controllers.DTOs.statsDTOs;

import com.it_academyproject.domains.Student;

public interface DTOStudentsExerciseNotTurnedInI {

    String getId();
    String getTeacher_id();

    String getFirst_Name();
    String getLast_Name();
    String getEmail();

    Integer getDaysLastTurnedIn();

}
