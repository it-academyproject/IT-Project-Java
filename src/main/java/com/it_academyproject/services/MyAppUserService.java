package com.it_academyproject.services;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyAppUserService {
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	//getAll
	public List<MyAppUser> getAllStudents(){
		int studentRoleId=1;
		return myAppUserRepository.findByRoleId(studentRoleId);
	}

	//get by name
	public ArrayList<MyAppUser> getByName(String firstName){
		return myAppUserRepository.findByFirstName(firstName);
	}

	//get by surName
	public ArrayList<MyAppUser> getBySurname(String lastName) {
		return myAppUserRepository.findByLastName(lastName);
	}

	//get by dni
	public MyAppUser getByDni(String idDocument) {
		return myAppUserRepository.findByIdDocument(idDocument);
	}

	//get by Id
	public MyAppUser getById(String id) {
		MyAppUser student = null;
		Optional<MyAppUser> studentOptional = myAppUserRepository.findById(id);
		System.out.println(id);
		if(studentOptional.isPresent()) {
			student = studentOptional.get();
		}else {
			System.out.println("Student not found 404");
		}
		System.out.println(student.getFirstName());
		return student;
	}
	
	// Put - Edit by dni
	public MyAppUser editGetByDni(MyAppUser student) {

		if(myAppUserRepository.existsById(student.getId())) {
		MyAppUser user = myAppUserRepository.findOneById(student.getId());
		user.setFirstName(student.getFirstName());
		user.setLastName(student.getLastName());
		System.out.println(user.getRole().getId());
		myAppUserRepository.save(user);

		 return user;
		 }else {return null;}
	}

	// Find number of users given a gender (M for male, F for female)
	public int usersByGender(char gender) {
		return myAppUserRepository.findByGender(gender).size();
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
