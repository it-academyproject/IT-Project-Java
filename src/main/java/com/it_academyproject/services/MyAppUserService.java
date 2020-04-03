package com.it_academyproject.services;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.Teacher;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyAppUserService {

	@Autowired
	MyAppUserRepository myAppUserRepository;

	@Autowired
	CourseService courseService;

	//getAll
	/*public List<MyAppUser> getAllStudents(){
		return updateStudentCourses(myAppUserRepository.findByRole(MyAppUser.Role.STUDENT));
	}*/

	private List<Student> updateStudentCourses(List<Student> students) {
		for (Student student : students) {
			student.setCourses(courseService.findByUserStudent(student));
		}
		return students;
	}

	//get by name
	/*public List<MyAppUser> getByName(String firstName){
		return updateStudentCourses(myAppUserRepository.findByFirstName(firstName));
	}

	//get by surName
	public List<MyAppUser> getBySurname(String lastName) {
		return updateStudentCourses(myAppUserRepository.findByLastName(lastName));
	}*/

	//get by Id
	public MyAppUser getById(String id) {
		MyAppUser myAppUser = null;
		Optional<MyAppUser> studentOptional = myAppUserRepository.findById(id);
		//System.out.println(id);
		if(studentOptional.isPresent()) {
			myAppUser = studentOptional.get();
		}else {
			System.out.println("User not found 404");
		}
		// System.out.println(myAppUser.getFirstName());
		return updateStudentCourses((Student) myAppUser);
	}

	private MyAppUser updateStudentCourses(Student student) {
		student.setCourses(courseService.findByUserStudent(student));
		return student;
	}

	// Update user by id
	public MyAppUser updateById(Student student) {

		if(myAppUserRepository.existsById(student.getId())) {
			MyAppUser user = myAppUserRepository.findOneById(student.getId());
			user.setFirstName(student.getFirstName());
			user.setLastName(student.getLastName());
			myAppUserRepository.save(user);

			return user;
		}else {return null;}
	}

	// Find number of users given a gender (M for male, F for female)
	public int usersByGender(char gender) {
		return myAppUserRepository.findByGender(gender).size();
	}

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

	public Teacher addTeacher(Teacher teacher) { return myAppUserRepository.save(teacher);}

	public List<Student> getStudents() {
		return (List<Student>) myAppUserRepository.findByRole(MyAppUser.Role.STUDENT);
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
