package com.it_academyproject.services;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.repositories.CourseRepository;

import java.util.List;

@Service
public class CourseService
{
	@Autowired
	CourseRepository courseRepository;

	// -------------------- -------------------- //
	
	public List<Course> findByUserStudent(Student student)
	{
		return courseRepository.findByUserStudent(student);
	}

	public List<Course> findByTeacher(Teacher teacher)
	{
		return courseRepository.findByTeacher(teacher);
	}

}