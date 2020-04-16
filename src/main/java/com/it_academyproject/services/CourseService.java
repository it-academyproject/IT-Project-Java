package com.it_academyproject.services;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.MyAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.repositories.CourseRepository;

import java.util.List;

@Service
public class CourseService {
	
	@Autowired
	CourseRepository courseRepository;

	public List<Course> findByUserStudent(MyAppUser student) {
		return courseRepository.findByUserStudent(student);
	}
}