package com.it_academyproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.tools.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

@RestController
public class MyAppUserController {
	
	@Autowired
	MyAppUserService myAppUserService;
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	@Autowired
	IterationRepository iterationRepository;
	
	//Call for students
	@JsonView(View.Summary.class)
	@GetMapping("/api/students")
	public List<MyAppUser> getAllStudents(){
		return myAppUserService.getAllStudents();
	}
	
	@JsonView(View.ShortDetails.class)
	@GetMapping("/api/students/short-details")
	public List<MyAppUser> getAllStudentsshortDetails()
	{
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
	
	//Call student by Id in JSON Body request
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/id")
	public MyAppUser getStudentById(@RequestBody MyAppUser student){
		return myAppUserService.getById(student.getId());
	}

	//Call student by Id as path variable
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/id/{id}")
	public MyAppUser getStudentById(@PathVariable String id){
		return myAppUserService.getById(id);
	}

	//Call students by iteration
//	@JsonView(View.SummaryWithOthers.class)
//	@GetMapping("api/students/iteration")
//	public MyAppUser getStudentByIteration(@RequestBody MyAppUser student){
//		return myAppUserService.getByIteration(student.getIterations());
//	}
	

	// Edit Student by id
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("api/students/id")
	public MyAppUser putStudentById(@RequestBody MyAppUser student){
		return myAppUserService.editStudent( student);

	}
		
//	@PutMapping("/api/students/{userId}")
//	public MyAppUser updateUser(@Valid @RequestBody Long iterationId, String userId) {
//		Iteration iteration = iterationRepository.findById(iterationId);
//		MyAppUser user = myAppUserRepository.findOneById(userId);
//		user.setIterations(iteration);
//		//iteration.setUsers(user);
//		return myAppUserRepository.save(user);
		
		
		
//		MyAppUser user = myAppUserRepository.findOneById(userId);
//		Iteration iteration = iterationRepository.findById(iterationId);
//		iteration.setUsers(user);
//		user.setIterations(iteration);	
		
}
	
	

