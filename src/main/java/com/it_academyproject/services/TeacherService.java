package com.it_academyproject.services;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.Teacher;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.it_academyproject.domains.MyAppUser.Role.TEACHER;

@Service
public class TeacherService {

	@Autowired
	MyAppUserRepository userRepository;

	@Autowired
	CourseService courseService;

	//getAll
	public List<Teacher> getAllTeachers(){
		return (List<Teacher>) userRepository.findByRole(TEACHER);
	}

	//get by name
	public List<Teacher> getByName(String firstName){
		return (List<Teacher>) userRepository.findByFirstNameAndRole(firstName, TEACHER);
	}

	//get by surName
	public List<Teacher> getBySurname(String lastName) {
		return (List<Teacher>) userRepository.findByLastNameAndRole(lastName, TEACHER);
	}

	//get by Id
	public MyAppUser getById(String id) {
		MyAppUser teacher = null;
		Optional<MyAppUser> teacherOptional = userRepository.findById(id);
		//System.out.println(id);
		if(teacherOptional.isPresent()) {
			teacher = teacherOptional.get();
		}else {
			System.out.println("Student not found 404");
		}
		// System.out.println(teacher.getFirstName());
		return userRepository.getOne(id);
	}

	// Update user by id
	public Teacher updateById(Teacher teacher) {

		if(userRepository.existsByIdAndRole(teacher.getId(), TEACHER)) {
			Teacher user = (Teacher) userRepository.findOneByIdAndRole(teacher.getId(), TEACHER);
			user.setFirstName(teacher.getFirstName());
			user.setLastName(teacher.getLastName());
			userRepository.save(user);

			return user;
		}else {return null;}
	}

	// Return full name given an id . Format "surname, name"
	public String getFullNameById(String teacherId) {
		MyAppUser teacher = getById(teacherId);
		return teacher.getLastName() + ", " + teacher.getFirstName();
	}

	public String getFirstNameById(String studentId) {
		return getById(studentId).getFirstName();
	}

	public String getLastNameById(String studentId) {
		return getById(studentId).getLastName();
	}

	public Teacher addTeacher(Teacher teacher) {
		return null;
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
