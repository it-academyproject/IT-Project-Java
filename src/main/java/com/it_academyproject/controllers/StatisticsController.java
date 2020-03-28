package com.it_academyproject.controllers;

import com.it_academyproject.controllers.statsDTOs.DTOStudentAbsence;
import com.it_academyproject.controllers.statsDTOs.DTOStudentsPerGender;
import com.it_academyproject.controllers.statsDTOs.DTOStudentsPerItinerary;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.services.StatisticsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StatisticsController
{
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    MyAppUserService myAppUserService;

	@GetMapping( "/api/statistics/per-itinerary" )
    public List<DTOStudentsPerItinerary> perItinerary(){

        Map<String, Integer> studentsPerItinerary = statisticsService.perItinerary();
        List<DTOStudentsPerItinerary> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: studentsPerItinerary.entrySet()) {
            result.add(new DTOStudentsPerItinerary(entry.getKey(), entry.getValue()));
        }
        return result;
    }

	@GetMapping( "/api/statistics/per-gender" )
    public DTOStudentsPerGender perGender( )
    {
        return new DTOStudentsPerGender(myAppUserService.usersByGender('M'), myAppUserService.usersByGender('F'));
    }

	@GetMapping( "/api/statistics/per-absence" )
    public List<DTOStudentAbsence> perAbsence()
    {
        List<DTOStudentAbsence> absences = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: statisticsService.perAbsence().entrySet()) {
            absences.add(new DTOStudentAbsence(myAppUserService.getFirstNameById(entry.getKey()), myAppUserService.getLastNameById(entry.getKey()), entry.getValue()));
        }
        return absences;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping( "/api/statistics/finish-in-x-days" )
    public ResponseEntity<String> finishInXdays()
    {
        try
        {
            String sendData = statisticsService.finishInXdays();
            return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            String exceptionMessage = e.getMessage();
            JSONObject sendData = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("type" , "error");
            message.put("message" , exceptionMessage);
            sendData.put("Message" , message);
            return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
        }
    }
}



