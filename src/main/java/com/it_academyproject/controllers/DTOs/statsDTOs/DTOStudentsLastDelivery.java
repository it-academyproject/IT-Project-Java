package com.it_academyproject.controllers.DTOs.statsDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface DTOStudentsLastDelivery {

    // These motherfuckers names have to be in relation to the dataBase! -> first_name, last_name
    String getFirst_name();
    String getLast_name();

    @JsonFormat(pattern="yyyy-MM-dd")
    Date getDate_status();
}
