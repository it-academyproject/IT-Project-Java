package com.it_academyproject.services;

import com.google.gson.Gson;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.exceptions.UserNotFoundException;

import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class StatisticsService
{

    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ItineraryRepository itineraryRepository;
    @Autowired
    MyAppUserRepository myAppUserRepository;


    
    public String perItinerary() throws Exception{
        List<Itinerary> numberOfItinerary = new ArrayList<>();
        itineraryRepository.findAll().forEach(numberOfItinerary::add);
        List<String> listPerItinerary = new ArrayList<>();
        for(int i=0;i<numberOfItinerary.size();i++) {
        	Itinerary itinerary = numberOfItinerary.get(i);       	
        	Integer numberPerItinerary = courseRepository.findByItinerary(itinerary).size();
        	listPerItinerary.add(numberPerItinerary.toString());        	
        }        
        ArrayList<ArrayList<String>> peopleByItinerary = new ArrayList<ArrayList<String>>();
        for (int i=0;i<numberOfItinerary.size();i++) {
        	String itineraryName = "Itinerario: " + numberOfItinerary.get(i).getName();
        	String itineraryStudent = "Cursando: " + listPerItinerary.get(i) + " estudiantes";
        	peopleByItinerary.add(peopleByItineraryMethod(itineraryName,itineraryStudent));
        }
        Gson Json= new Gson();
        String sendData = Json.toJson(peopleByItinerary);
    	return sendData;
    }
    
    public String perGender() throws Exception {
        List<Character> typeOfGender = new ArrayList<Character>();
        	typeOfGender.add('M');
        	typeOfGender.add('F');
    	List<String> listNumberOfUsers = new ArrayList<>();
        for(int i=0;i<typeOfGender.size();i++) {
        	Character gender = typeOfGender.get(i);
        	Integer numberPerUsers = myAppUserRepository.findByGender(gender).size();
        	listNumberOfUsers.add(numberPerUsers.toString());
        }
        ArrayList<ArrayList<String>> peopleByGender = new ArrayList<ArrayList<String>>();
        for(int i=0;i<typeOfGender.size();i++) {
        	peopleByGender.add(peopleByGenderMethod(typeOfGender.get(i),(listNumberOfUsers.get(i) + " estudiantes")));
        }
        for (int i=0;i<peopleByGender.size();i++) {
        	if(peopleByGender.get(i).get(i).equalsIgnoreCase("M")) {
        		peopleByGender.get(i).set(0, "MASCULINO:");
        	}else {
        		peopleByGender.get(i).set(0, "FEMENINO:");
        	}
        }
        Gson Json = new Gson();        
        String sendData = Json.toJson(peopleByGender);
        return sendData;
    }
    
    public String perAbsence() throws Exception {
    	List<Absence> absence = new ArrayList<>();
    	absenceRepository.findAll().forEach(absence::add);
    	HashMap<String,String>numberOfAbsence = new HashMap<>();
    	for(int i = 0; i < absence.size(); i++) {  
            Integer counter = 0; 
    		for(int j = i + 1; j < absence.size(); j++) {  
                 if(absence.get(i).getUserStudent().getId().equals(absence.get(j).getUserStudent().getId())) {  
                    counter++;  
                 }
             }
    		if(counter>=8) {    			
    			numberOfAbsence.put(("Nombre: " + absence.get(i).getUserStudent().getFirstName() + " " + absence.get(i).getUserStudent().getLastName()), counter.toString() + 
    					" faltas");
    		}   		
    	}
    	
    	Gson Json = new Gson();
    	String sendData = Json.toJson(numberOfAbsence);
        return sendData;
    }
    
    public String finishInXdays() throws Exception {
        List<Course> finishInXDays = new ArrayList<>();
        courseRepository.findAll().forEach(finishInXDays::add);
        Date currentDate = Calendar.getInstance().getTime();
        List<Course> endListOfUsersEndingInLessThanXDays = new ArrayList<>();
        for(int i=0;i<finishInXDays.size();i++) {
        	if (finishInXDays.get(i).getEndDate() != null) {
        		Long days = (Long) ((finishInXDays.get(i).getEndDate().getTime()-currentDate.getTime())/86400000);
        		if (days<=15) {
        			endListOfUsersEndingInLessThanXDays.add(finishInXDays.get(i));
        		}
        	}
        }
        ArrayList<ArrayList<String>> endListNameAndDaysLeft = new ArrayList<ArrayList<String>>();
        for(int i=0;i<endListOfUsersEndingInLessThanXDays.size();i++) {
        	Long day =(Long) ((endListOfUsersEndingInLessThanXDays.get(i).getEndDate().getTime()-currentDate.getTime())/86400000);
        	endListNameAndDaysLeft.add(endDayMethod("Nombre: " + endListOfUsersEndingInLessThanXDays.get(i).getUserStudent().getFirstName() + " " + 
					endListOfUsersEndingInLessThanXDays.get(i).getUserStudent().getLastName(),"Dias restantes: " + day.toString()));
        }
        
        Gson Json = new Gson();
    	String sendData = Json.toJson(endListNameAndDaysLeft);
        return sendData;
    }
    public List<MyAppUser> getAllActiveStudents ( ) {
        List<Course> courseList = courseRepository.findByEndDate( null );
        List<MyAppUser> activeStudents = new ArrayList<>();
        Course course;
        MyAppUser myAppUser;
        for (int i = 0; i < courseList.size() ; i++)
        {
            course = courseList.get(i);
            MyAppUser student = course.getUserStudent();
            myAppUser = myAppUserRepository.findOneById( student.getId() )
            		.orElseThrow(() -> new ResourceNotFoundException("not found"));
            if ( myAppUser != null )
            {
                activeStudents.add( myAppUser );
            }
            else
            {
                throw (new UserNotFoundException( student.getId() ));
            }
        }
        return (activeStudents);
    }
    public static ArrayList<String> peopleByItineraryMethod(String itinerary, String numberOfPeople) {
    	ArrayList<String> itineraryObject = new ArrayList<>();
    	itineraryObject.add(itinerary);
    	itineraryObject.add(numberOfPeople);
    	return itineraryObject;
    }
    public static ArrayList<String> peopleByGenderMethod (Character typeOfGender, String numberOfUsers){
    	ArrayList<String> peopleByGenderList = new ArrayList<String>();
    	peopleByGenderList.add(typeOfGender.toString());
    	peopleByGenderList.add(numberOfUsers);
    	return peopleByGenderList;
    }
    public static ArrayList<String> AbsenceMethod (String name, String absence){
    	ArrayList<String> absenceList = new ArrayList<String>();
    	absenceList.add(name);
    	absenceList.add(absence);
    	return absenceList;
    }
    public static ArrayList<String> endDayMethod (String name, String daysLeft){
    	ArrayList<String> endDayList = new ArrayList<String>();
    	endDayList.add(name);
    	endDayList.add(daysLeft);
    	return endDayList;
    }
}
