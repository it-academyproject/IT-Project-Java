package com.it_academyproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.tools.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.MyAppUser;

@RestController
public class MyAppUserController {
	
	@Autowired
	MyAppUserService myAppUserService;
	
	//Call for students
	@JsonView(View.Summary.class)
	@GetMapping("/api/students")
	public List<MyAppUser> getAllStudents(){
		return myAppUserService.getAllStudents();
	}
	
	//Call students by name
	@JsonView(View.Summary.class)
	@GetMapping("api/students/name")
	public List<MyAppUser> getStudentsByName(@RequestBody MyAppUser student){
		return myAppUserService.getByName(student.getFirstName());
	}
	
	//Call students by surname
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/surname")
	public List<MyAppUser> getStudentsBySurname(@RequestBody MyAppUser student){
		return myAppUserService.getBySurname(student.getLastName());
	}
	
	//Call student by Id
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/id")
	public MyAppUser getStudentById(@RequestBody MyAppUser student){
		return myAppUserService.getById(student.getId());
	}
	
	// Edit Student by id
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/students/id")
	public MyAppUser putStudentById(@RequestBody MyAppUser student){
		return myAppUserService.editStudent( student);
	}
}