package com.it_academyproject.services;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

	@Autowired
	MyAppUserRepository userRepository;

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
			userRepository.save(repoStudent);

			return repoStudent;

		} else {
			return null;
		}
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
