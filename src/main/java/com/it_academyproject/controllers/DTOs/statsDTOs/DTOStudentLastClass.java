package com.it_academyproject.controllers.DTOs.statsDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({ "first_name", "last_name", "daysLastClass"})
public class DTOStudentLastClass {

    public String first_name;
    public String last_name;

    public int daysLastClass;

    public DTOStudentLastClass() {
    }

    public DTOStudentLastClass(String first_name, String last_name, int daysLastClass) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.daysLastClass = daysLastClass;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getDaysLastClass() {
        return daysLastClass;
    }

    public void setDaysLastClass(int daysLastClass) {
        this.daysLastClass = daysLastClass;
    }

}
