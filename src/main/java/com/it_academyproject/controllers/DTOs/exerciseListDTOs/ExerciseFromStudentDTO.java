package com.it_academyproject.controllers.DTOs.exerciseListDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.it_academyproject.domains.UserExercise;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseFromStudentDTO {

    private String name;
    private int id;
    private StatusDTO status;
    private ItineraryDTO itinerary;
    private TeacherDTO teacher;

    ExerciseFromStudentDTO (UserExercise exercise) {
        name = exercise.getExercise().getName();
        id = exercise.getExercise().getId();
        status = new StatusDTO(exercise.getStatusExercise().getName(),
                exercise.getStatusExercise().getId(), exercise.getDate_status());
        itinerary = new ItineraryDTO(exercise.getExercise().getItinerary().getName(), exercise.getExercise().getItinerary().getId());
        if (exercise.getUserTeacher() != null)
            teacher = new TeacherDTO(exercise.getUserTeacher().getLastName() + ", " + exercise.getUserTeacher().getFirstName(), exercise.getUserTeacher().getId());
    }

    public static List<ExerciseFromStudentDTO> getList (List<UserExercise> exercises) {
        List<ExerciseFromStudentDTO> result = new ArrayList<>();
        exercises.forEach(exercise -> result.add(new ExerciseFromStudentDTO(exercise)));
        return result;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public ItineraryDTO getItinerary() {
        return itinerary;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    private class StatusDTO {
        private String name;
        private int id;
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        private Date date;

        public StatusDTO(String name, int id, Date date) {
            this.name = name;
            this.id = id;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public Date getDate() {
            return date;
        }
    }

    private class ItineraryDTO {
        private String name;
        private int id;

        public ItineraryDTO(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    private class TeacherDTO {
        private String name;
        private String id;

        public TeacherDTO(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}

