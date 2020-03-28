package com.it_academyproject.controllers.statsDTOs;

public class DTOAbsences {
    
    private final String firstName;
    private final String lastName;
    private final int absences;

    public DTOAbsences(String firstName, String lastName, int absences) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.absences = absences;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAbsences() {
        return absences;
    }
}
