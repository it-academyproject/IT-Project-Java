package com.it_academyproject.controllers;

import com.it_academyproject.controllers.statsDTOs.DTOAbsences;
import com.it_academyproject.controllers.statsDTOs.DTOGender;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.services.StatisticsService;
import org.json.JSONArray;
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
    public List<DTOItinerary> perItinerary(){

        Map<String, Integer> itineraryMap = statisticsService.perItineraryNew();
        List<DTOItinerary> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: itineraryMap.entrySet()) {
            result.add(new DTOItinerary(entry));
        }
        return result;
    }

	@GetMapping( "/api/statistics/per-gender" )
    //public ResponseEntity<String> perGender( )
    public DTOGender perGender( )
    {
        return new DTOGender(myAppUserService.usersByGender('M'), myAppUserService.usersByGender('F'));
/*        try
        {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(statisticsService.perGender().toString());
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
        }*/
    }

    //@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping( "/api/statistics/per-absence" )
    //public ResponseEntity<String> perAbsence()
    public List<DTOAbsences> perAbsence()
    {
        List<DTOAbsences> absences = new ArrayList<>();
        //Map<String, Integer> absencesMap = statisticsService.perAbsence();
        for (Map.Entry<String, Integer> entry: statisticsService.perAbsence().entrySet()) {
//            DTOAbsences absence = new DTOAbsences(myAppUserService.getFirstNameById(entry.getKey()), myAppUserService.getLastNameById(entry.getKey()), entry.getValue());
            absences.add(new DTOAbsences(myAppUserService.getFirstNameById(entry.getKey()), myAppUserService.getLastNameById(entry.getKey()), entry.getValue()));
        }
        return absences;
/*
        try
        {
            Map<String, Integer> absencesMap = statisticsService.perAbsence();
            String sendData = statisticsService.perAbsenceOld();
            */
/*System.out.println("************* OLD CALLING RESULT ******************");
            System.out.println(sendData);
            System.out.println("************* NEW CALLING RESULT ******************");
            System.out.println(absencesMap==null?"null":absencesMap.toString());*//*

            JSONArray response = absencesJson(absencesMap);
            return new ResponseEntity( sendData.toString() , HttpStatus.OK);
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
*/
    }

    private JSONArray absencesJson(Map<String, Integer> absencesMap) {

	    JSONObject student = new JSONObject();
	    JSONArray result = new JSONArray();

        for (Map.Entry<String, Integer> entry : absencesMap.entrySet()) {
            student.put("absences", entry.getValue());
            student.put("student", myAppUserService.getFullnameById(entry.getKey()));
            System.out.println("******************* JSON Object *****************");
            System.out.println(student.toString());
            System.out.println("******************* JSON Object END *****************");
            //result.
        }
        System.out.println("********************* JSON ARRAY *********************");
        System.out.println(result.toString());
        System.out.println("********************* JSON ARRAY FROM MAP *********************");
        result = new JSONArray(absencesMap);
        System.out.println(result.toString());
        //result.put(absencesMap);

        return result;
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

    private class DTOItinerary {

	    private final String itinerary;
	    private final int students;

        public DTOItinerary(Map.Entry<String, Integer> entry) {
            itinerary=entry.getKey();
            students =entry.getValue();
        }

        public String getItinerary() {
            return itinerary;
        }

        public int getStudents() {
            return students;
        }
    }
}



