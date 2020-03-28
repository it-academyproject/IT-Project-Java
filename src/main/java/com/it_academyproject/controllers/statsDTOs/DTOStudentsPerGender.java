package com.it_academyproject.controllers.statsDTOs;

public class DTOStudentsPerGender {

    private final int male;
    private final int female;

    public DTOStudentsPerGender(int male, int female) {
        this.male = male;
        this.female = female;
    }

    public int getMale() {
        return male;
    }

    public int getFemale() {
        return female;
    }
}
