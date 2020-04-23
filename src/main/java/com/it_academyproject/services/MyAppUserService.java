package com.it_academyproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.it_academyproject.exceptions.BadRoleException;
import com.it_academyproject.exceptions.GenericResponse;
import com.it_academyproject.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

@Service
public class MyAppUserService {

	private static final int ROLE_ID = 1;
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	@Autowired
	IterationRepository iterationRepository;

	@Autowired
	CourseService courseService;
	
	//getAll
	public List<MyAppUser> getAllStudents(){
		return updateStudentCourses(myAppUserRepository.findByRoleId(1));
	}
	private List<MyAppUser> updateStudentCourses(List<MyAppUser> students) {
		for (MyAppUser student : students) {
			student.setCourses(courseService.findByUserStudent(student));
		}
		return students;
	}

	//get by name
	public List<MyAppUser> getByName(String firstName){
		return updateStudentCourses(myAppUserRepository.findByFirstName(firstName));
	}

	//get by surName
	public List<MyAppUser> getBySurname(String lastName) {
		return updateStudentCourses(myAppUserRepository.findByLastName(lastName));
	}

	private MyAppUser updateStudentCourses(MyAppUser student) {
		student.setCourses(courseService.findByUserStudent(student));
		return student;
	}


	//get by Id
	public MyAppUser getById(String id) {
		MyAppUser student = null;
		Optional<MyAppUser> studentOptional = myAppUserRepository.findById(id);
		if(studentOptional.isPresent()) {
			student = studentOptional.get();
		}else {
			System.out.println("Student not found 404");
		}
		return student;
	}

	// get student by Id
	public Optional<MyAppUser> getStudentById (String id) {
		MyAppUser student = getById(id);
		if (student!=null) {
			if (student.getRole().getId() != ROLE_ID) throw new BadRoleException("User is not a student");
		}
		return Optional.ofNullable(student);
	}
	
	//get by Iteration
//		public MyAppUser getByIteration(Set<Iteration> set) {
//			MyAppUser student = null;
//			Optional<MyAppUser> studentOptional = MyAppUserRepository.findById(set);
//			System.out.println(set);
//			if(studentOptional.isPresent()) {
//				student = studentOptional.get();
//			}else {
//				System.out.println("Student not found 404");
//			}
//			System.out.println(student.getFirstName());
//			return student;
//		}
	

	// Edit student
	public MyAppUser editStudent(MyAppUser student) {

		if(myAppUserRepository.existsById(student.getId())) {

			MyAppUser user = myAppUserRepository.findOneById(student.getId())
			.orElseThrow(() -> new ResourceNotFoundException("not found"));
			if (student.getFirstName()!=null && !student.getFirstName().isEmpty())
				user.setFirstName(student.getFirstName());
			if (student.getLastName()!=null && !student.getLastName().isEmpty())
				user.setLastName(student.getLastName());
			myAppUserRepository.save(user);

			return user;

		} else {
			return null;
		}
	}

	// Find number of users given a gender (M for male, F for female)
	public int usersByGender(char gender) {
		return myAppUserRepository.findByGender(gender).size();
	}

	// Return full name given an id . Format "surname, name"
	public String getFullnameById(String studentId) {
		MyAppUser student = getById(studentId);
		return student.getLastName() + ", " + student.getFirstName();
	}

	public String getFirstNameById(String studentId) {
		return getById(studentId).getFirstName();
	}

	public String getLastNameById(String studentId) {
		return getById(studentId).getLastName();
	}

/*
	// find the user by the email passed to the repository
	static public MyAppUser findUserByEmail(final String email){
		return myAppUserRepository.findByEmail(email);
	}

	// Stores the user token generated in the controller
	public static void createPasswordResetTokenForUser(MyAppUser user, String token){
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, MyAppUser user) {
		String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
		String message = messages.getMessage("message.resetPassword", null, locale);
		return constructEmail("Reset Password", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, MyAppUser user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	public static void changeUserPassword(MyAppUser user, String password){
		user.setPassword(passwordEncoder.encode(password));
		myAppUserRepository.save(user);
	}
*/
}

