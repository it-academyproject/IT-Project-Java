package com.it_academyproject.controllers.DTOs.statsDTOs;

public class DTOStudentsFinishXDays {
    private String id;
    private String firstName;
    private String lastName;
    private long days;

    public DTOStudentsFinishXDays(){}
    public DTOStudentsFinishXDays(String id, String firstName, String lastName, long days) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

}
