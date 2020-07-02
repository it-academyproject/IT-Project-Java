package com.it_academyproject.controllers.DTOs.statsDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@JsonPropertyOrder({ "first_name", "last_name", "daysLastClass"})
public interface DTOStudentLastClass {

    String getFirst_Name();
    String getLast_Name();

    int getDaysLastClass();

}
