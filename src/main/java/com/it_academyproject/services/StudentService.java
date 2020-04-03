package com.it_academyproject.services;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

	@Autowired
	MyAppUserRepository userRepository;

	@Autowired
	CourseService courseService;

	//getAll
	public List<Student> getAllStudents(){
		return (List<Student>) userRepository.findByRole(MyAppUser.Role.STUDENT);
	}

	//get by name
	public List<Student> getByName(String firstName){
		return (List<Student>) userRepository.findByFirstNameAndRole(firstName, MyAppUser.Role.STUDENT);
	}

	//get by surName
	public List<Student> getBySurname(String lastName) {
		return (List<Student>) userRepository.findByLastNameAndRole(lastName, MyAppUser.Role.STUDENT);
	}

	//get by Id
	public Student getById(String id) {
		return (Student) userRepository.findOneByIdAndRole(id, MyAppUser.Role.STUDENT);
	}

	// Update user by id
	public Student update(Student student) {

		if(userRepository.existsByIdAndRole(student.getId(), MyAppUser.Role.STUDENT)) {
			Student user = (Student) userRepository.findOneById(student.getId());
			user.setFirstName(student.getFirstName());
			user.setLastName(student.getLastName());
			userRepository.save(user);

			return user;
		}else {return null;}
	}

	// Find number of users given a gender (M for male, F for female)
	public int usersByGender(char gender) {
		return userRepository.findByGender(gender).size();
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
		return (Student) userRepository.findOneById(id);
	}

	public Student addStudent(Student student) {
		return userRepository.save(student);
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
