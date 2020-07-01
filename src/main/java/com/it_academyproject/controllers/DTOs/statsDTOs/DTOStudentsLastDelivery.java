package com.it_academyproject.controllers.DTOs.statsDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface DTOStudentsLastDelivery {

    String getFirst_name();
    String getLast_name();

    //Integer getDaysDiff();

    @JsonFormat(pattern="yyyy-MM-dd")
    Date getDate_status();
}
