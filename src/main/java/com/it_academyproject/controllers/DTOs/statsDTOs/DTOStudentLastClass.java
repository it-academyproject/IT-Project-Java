package com.it_academyproject.controllers.DTOs.statsDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({ "first_name", "last_name", "days_last_class"})
public class DTOStudentLastClass {

    public String first_name;
    public String last_name;

    public int days_last_class;

    public DTOStudentLastClass() {
    }

    public DTOStudentLastClass(String first_name, String last_name, int days_last_class) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.days_last_class = days_last_class;
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

    public int getDays_last_class() {
        return days_last_class;
    }

    public void setDays_last_class(int days_last_class) {
        this.days_last_class = days_last_class;
    }

}