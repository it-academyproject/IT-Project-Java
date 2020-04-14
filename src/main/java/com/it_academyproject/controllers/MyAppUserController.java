package com.it_academyproject.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.services.StudentService;
import com.it_academyproject.tools.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyAppUserController {
	
	@Autowired
	StudentService studentService;

	MyAppUserService myAppUserService;
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	@Autowired
	IterationRepository iterationRepository;
	
	//Call for students
	//@JsonView(View.Summary.class)
	@JsonView(View.Summary.class)
	@GetMapping("/api/students")
	public List<Student> getAllStudents(){
		return studentService.getAllStudents();
	}
	
	//Call students by name
	@JsonView(View.Summary.class)
	@GetMapping("/api/students/name")
	public List<Student> getStudentsByName(@RequestBody Student student){
		return studentService.getByName(student.getFirstName());
	}
	
	//Call students by surname
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("/api/students/surname")
	public List<Student> getStudentsBySurname(@RequestBody Student student){
		return studentService.getBySurname(student.getLastName());
	}
	
	//Call student by Id
	@JsonView(View.SummaryWithOthers.class)
	@GetMapping("/api/students/id")
	public Student getStudentById(@RequestBody Student student){
		return studentService.getById(student.getId());
	}
	
	//Call students by iteration
//	@JsonView(View.SummaryWithOthers.class)
//	@GetMapping("api/students/iteration")
//	public MyAppUser getStudentByIteration(@RequestBody MyAppUser student){
//		return myAppUserService.getByIteration(student.getIterations());
//	}
	

	// Edit Student by id
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("/api/students/id")
	public Student updateStudent(@RequestBody Student student){
		return studentService.editStudent(student);
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
	
	

