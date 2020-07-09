package com.it_academyproject.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.controllers.DTOs.itineraryDTOs.DTOItinerariesLastDelivery;
import com.it_academyproject.controllers.DTOs.statsDTOs.*;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.StatusExercise;
import com.it_academyproject.domains.Student;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.StatusExerciseRepository;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentAbsence;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsFinishXDays;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsPerGender;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsPerItinerary;
import com.it_academyproject.services.StatisticsService;
import com.it_academyproject.services.StudentService;
import com.it_academyproject.tools.View;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api/stats")
public class StatisticsController {
	@Autowired
	StatisticsService statisticsService;
	@Autowired
	StudentService studentService;
	@Autowired
	ItineraryRepository itineraryRepository;

	@GetMapping("/per-itinerary")
	public List<DTOStudentsPerItinerary> perItinerary() {

		Map<String, Integer> studentsPerItinerary = statisticsService.perItinerary();
		List<DTOStudentsPerItinerary> result = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : studentsPerItinerary.entrySet()) {
			result.add(new DTOStudentsPerItinerary(entry.getKey(), entry.getValue()));
		}
		return result;
	}

	@GetMapping("/per-gender")
	public DTOStudentsPerGender perGender() {
		return new DTOStudentsPerGender(studentService.studentsByGender('M'), studentService.studentsByGender('F'));
	}

	@GetMapping("/per-absence")
	public List<DTOStudentAbsence> perAbsence() {
		List<DTOStudentAbsence> absences = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : statisticsService.perAbsence().entrySet()) {
			absences.add(new DTOStudentAbsence(studentService.getUserById(entry.getKey()),
					studentService.getFirstNameById(entry.getKey()), studentService.getLastNameById(entry.getKey()),entry.getValue()));
		}
		return absences;
	}

	@GetMapping("/finish-in-x-days/{xDays}")
	public List<DTOStudentsFinishXDays> finishInXdays(@PathVariable int xDays){
		return statisticsService.finishInXdays(xDays);
	}


	@GetMapping("/students-deliveries")
	public List<DTOStudentsLastDelivery> students_deliveries() {
		//return repo.bla("FINISHED", LocalDateTime.of(2019, Month.NOVEMBER, 30, 01, 00, 00));
		return studentService.students_deliveries(3, LocalDateTime.now() );
		// LocalDateTime.of(2019, Month.OCTOBER, 11, 0,0,0)
		// LocalDateTime.now()
	}

	@GetMapping("/itineraries-deliveries")
	public List<DTOItinerariesLastDelivery> itinerarys_deliveries() {
		return itineraryRepository.itinerarys_deliveries(3, LocalDateTime.now() );
		// LocalDateTime.of(2019, Month.OCTOBER, 11, 0,0,0)
		// LocalDateTime.now()
	}

	@GetMapping("/students-attendance")
	public List<DTOStudentLastClass>  getUsersLastClass() {
		return studentService.getUsersLastClass();
	}

}
