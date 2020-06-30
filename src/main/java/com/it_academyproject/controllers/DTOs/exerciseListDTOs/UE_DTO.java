package com.it_academyproject.controllers.DTOs.exerciseListDTOs;

import com.it_academyproject.domains.UserExercise;

public class UE_DTO {
    int userExerciseId;
    String studentId;
    String status;
    int exerciseId;

    public UE_DTO(){}
    public UE_DTO(UserExercise userExercise){
        this.userExerciseId = userExercise.getId();
        this.studentId = userExercise.getUserStudent().getId();
        this.status = userExercise.getStatus().getName();
        this.exerciseId = userExercise.getExercise().getId();
    }

    public int getUserExerciseId() {
        return userExerciseId;
    }

    public void setUserExerciseId(int userExerciseId) {
        this.userExerciseId = userExerciseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudenId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
