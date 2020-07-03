package com.it_academyproject.services;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentLastClass;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentLastClassI;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsLastDelivery;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

@Service
public class StudentService {

	@Autowired
	MyAppUserRepository userRepository;
	
	@Autowired
	UserExerciseService userExerciseService;
	
	@Autowired
	CourseService courseService;

	//getAll
	public List<Student> getAllStudents(){
		return updateStudentCourses((List<Student>) userRepository.findByRole(MyAppUser.Role.STUDENT));
	}

	private List<Student> updateStudentCourses(List<Student> students) {
		for (Student student : students) {
			student.setCourses(courseService.findByUserStudent(student));
			student.setLastDeliveredExercise(userExerciseService.getLastDeliveredExerciseDate(student));
		}
		return students;
	}
		
	
	//get by name
	public List<Student> getByName(String firstName){
		return (List<Student>) userRepository.findByFirstNameAndRole(firstName, MyAppUser.Role.STUDENT);
	}

	//get by surName
	public List<Student> getBySurname(String lastName) {
		return (List<Student>) userRepository.findByLastNameAndRole(lastName, MyAppUser.Role.STUDENT);
	}
//get all user with their id
	public String getUserById(String id) {
		return id;
	}
	//get by Id
	public Student getById(String id) {
		return (Student) userRepository.findOneByIdAndRole(id, MyAppUser.Role.STUDENT);
	}

	public int studentsByGender(char gender) { return userRepository.findByGenderAndRole(gender, MyAppUser.Role.STUDENT).size();}

	// Return full name given an id . Format "surname, name"
	public String getFullNameById(String studentId) {
		MyAppUser student = getById(studentId);
		return student.getLastName() + ", " + student.getFirstName();
	}

	public String getFirstNameById(String studentId) {
		return getById(studentId).getFirstName();
	}

	public String getLastNameById(String studentId) {
		return getById(studentId).getLastName();
	}

	public Student findOneById(String id) {
		return (Student) userRepository.findOneById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	public Student addStudent(Student student) {
		return userRepository.save(student);
	}

	public Student editStudent(Student student) {

		if(userRepository.existsByIdAndRole(student.getId(), MyAppUser.Role.STUDENT)) {

			Student repoStudent = (Student) userRepository.findOneById(student.getId())
					.orElseThrow(() -> new UserNotFoundException("User not found"));
			if (student.getFirstName()!=null && !student.getFirstName().isEmpty())
				repoStudent.setFirstName(student.getFirstName());
			if (student.getLastName()!=null && !student.getLastName().isEmpty())
				repoStudent.setLastName(student.getLastName());
			// Update enabled if needed
			if (student.isEnabled()!=repoStudent.isEnabled())
				repoStudent.setEnabled(student.isEnabled());

			userRepository.save(repoStudent);

			return repoStudent;

		} else {
			return null;
		}
	}
	

	public List<DTOStudentsLastDelivery> students_deliveries(Integer statusId, LocalDateTime destDate){
		return userRepository.students_deliveries(statusId,destDate);
	}

	public List<DTOStudentLastClass> getUsersLastClass() {

		List<DTOStudentLastClassI> students = userRepository.getUsersLastClass();
		List<DTOStudentLastClass> dtos = new ArrayList<>();

		for(int i = 0; i < students.size(); i++) {
			DTOStudentLastClass student = new DTOStudentLastClass();

			student.setFirst_name(initCap(students.get(i).getFirst_Name()));
			student.setLast_name(initCap(students.get(i).getLast_Name()));
			//student.setDaysLastClass(getDaysBeetwenDates());
			student.setDaysLastClass(students.get(i).getDaysLastClass());

			dtos.add(student);
		}

		return dtos;
	}

	public static String initCap(String stringValue) {

		String[] words = stringValue.toLowerCase().split(" ");

		StringBuilder strBuild = new StringBuilder();

		for(int i = 0; i < words.length; i++) {
			strBuild.append(Character.toUpperCase(words[i].charAt(0)));
			strBuild.append(words[i].substring(1));
			if(i < words.length - 1) {
				strBuild.append(' ');
			}
		}

		return strBuild.toString();

	}

	public static int getDaysBeetwenDates(CharSequence pDateIni) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateIni = LocalDate.parse(pDateIni, formatter);
		LocalDate dateNow = LocalDate.now();

		int daysLastClass;

		daysLastClass = (int)ChronoUnit.DAYS.between(dateIni, dateNow);

		return daysLastClass;
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
