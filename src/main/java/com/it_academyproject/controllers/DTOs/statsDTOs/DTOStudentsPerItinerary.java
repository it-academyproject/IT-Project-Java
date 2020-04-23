package com.it_academyproject.controllers.DTOs.statsDTOs;

public class DTOStudentsPerItinerary {

    private final String itinerary;
    private final int students;

    public DTOStudentsPerItinerary(String itinerary, int students) {
        this.itinerary = itinerary;
        this.students = students;
    }

    public String getItinerary() {
        return itinerary;
    }

    public int getStudents() {
        return students;
    }
}
