package com.it_academyproject.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Student;
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

	//Call for students
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
	
	//Edit Student by Id document
	@JsonView(View.SummaryWithOthers.class)
	@PutMapping("/api/students/id")
	public Student updateStudent(@RequestBody Student student){
		return studentService.update(student);
	}
}