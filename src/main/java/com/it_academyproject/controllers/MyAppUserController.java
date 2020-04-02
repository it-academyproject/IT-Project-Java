package com.it_academyproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	//@JsonView(View.Summary.class)
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
	
	//Call students by dni
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/dni")
	public MyAppUser getStudentByDni(@RequestBody MyAppUser student){
		return myAppUserService.getByDni(student.getIdDocument());
	}
		
	//Call student by Id
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("api/students/id")
	public MyAppUser getStudentById(@RequestBody MyAppUser student){
		return myAppUserService.getById(student.getId());
	}
	
	//Call students by iteration
//	@JsonView(View.SummaryWithOthers.class)
//	@GetMapping("api/students/iteration")
//	public MyAppUser getStudentByIteration(@RequestBody MyAppUser student){
//		return myAppUserService.getByIteration(student.getIterations());
//	}
	
	//Edit Student by Id document
	@JsonView(View.SummaryWithOthers.class)
	//@PutMapping("api/students")
	public MyAppUser PutStudentByDni(@RequestBody MyAppUser student){
		return myAppUserService.editGetByDni( student);
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
	
	

