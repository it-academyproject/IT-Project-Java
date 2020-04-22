package com.it_academyproject.controllers.DTOs.exerciseListDTOs;

import com.it_academyproject.domains.UserExercice;

import java.util.ArrayList;
import java.util.List;

public class ExercisesFromStudentDTO {

    private String studentId;
    private List<ExerciseDTO> exercises;

    public ExercisesFromStudentDTO(String studentId, List<UserExercice> userExercices) {
        this.studentId = studentId;
        this.exercises = ExerciseDTO.getExercisesDTO(userExercices);
        /*this.exercises = new ArrayList<>();
        userExercices.forEach(userExercice -> exercises.add(new ExerciseDTO(userExercice)));*/
    }

    /*public static ExercisesFromStudentDTO userExercisesList(String id, List<UserExercice> exercises) {
        return new ExercisesFromStudentDTO()
    }*/

    public String getStudentId() {
        return studentId;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    static class ExerciseDTO {

        private String itinerary;
        private String name;
        private String status;
        private String teacher;

        ExerciseDTO (UserExercice exercise) {
            itinerary = exercise.getExercice().getItinerary().getName();
            name = exercise.getExercice().getName();
            status = exercise.getStatusExercice().getName();
            if (exercise.getUserTeacher()!=null)
                teacher = exercise.getUserTeacher().getLastName() + ", " + exercise.getUserTeacher().getFirstName();
            else teacher = "Not assigned";
        }

        static List<ExerciseDTO> getExercisesDTO(List<UserExercice> userExercises) {
            List<ExerciseDTO> exerciseDTOList = new ArrayList<>();
            userExercises.forEach( userExercise ->
                    exerciseDTOList.add(new ExerciseDTO(userExercise)));
            return exerciseDTOList;
        }

        public String getItinerary() {
            return itinerary;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }

        public String getTeacher() {
            return teacher;
        }
    }
}
