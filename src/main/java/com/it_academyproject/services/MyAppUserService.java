package com.it_academyproject.services;

import java.util.List;
import java.util.Optional;

import com.it_academyproject.exceptions.BadRoleException;
import com.it_academyproject.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.MyAppUser.Role;
import com.it_academyproject.domains.Student;
import com.it_academyproject.domains.Teacher;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

@Service
public class MyAppUserService
{
	private static final Role ROLE_ID = Role.STUDENT;

	@Autowired
	MyAppUserRepository myAppUserRepository;

	@Autowired
	IterationRepository iterationRepository;

	@Autowired
	CourseService courseService;

	@Autowired
	AbsencesService absencesService;

	// -------------------- -------------------- //

	public List<Student> getAllStudents()
	{
		return updateStudentCourses((List<Student>) myAppUserRepository.findByRole(MyAppUser.Role.STUDENT));
	}

	private List<Student> updateStudentCourses(List<Student> students)
	{
		for (Student student : students)
		{
			student.setCourses(courseService.findByUserStudent(student));
		}
		return students;
	}

	/*
	// in dev branch when merging. Substituted by previous function in b-30
	 
	public List<MyAppUser> getAllStudents()
	{ 
		return updateStudentCourses(myAppUserRepository.findByRoleId(1)); 
	}
	 
	private List<MyAppUser> updateStudentCourses(List<MyAppUser> students) 
	{ 
		for (MyAppUser student : students) 
		{
			student.setCourses(courseService.findByUserStudent(student)); 
		} 
		return students; 
	}
	*/

	/*
	public List<MyAppUser> getByName(String firstName)
	{ 
		return updateStudentCourses(myAppUserRepository.findByFirstName(firstName)); 
	}
	
	public List<MyAppUser> getBySurname(String lastName) 
	{
		return updateStudentCourses(myAppUserRepository.findByLastName(lastName)); 
	}
	*/

	public Student getById(String id)
	{
		System.out.println(id);
		Student myAppUser = null;
		Optional<MyAppUser> studentOptional = myAppUserRepository.findById(id);
		if (studentOptional.isPresent())
		{
			myAppUser = (Student) studentOptional.get();
			myAppUser.setCourses(courseService.findByUserStudent(myAppUser));
			myAppUser.setTotalAbsences(absencesService.getAbsenceByStudentId(myAppUser).size());
		} 
		else
		{
			System.out.println("User not found 404");
		}
		// System.out.println(myAppUser.getFirstName());
		return updateStudentCourses((Student) myAppUser);
	}

	private Student updateStudentCourses(Student student)
	{
		student.setCourses(courseService.findByUserStudent(student));
		return student;
	}

	public Optional<Student> getStudentById(String id)
	{
		Student student = (Student) getById(id);
		if (student != null)
		{
			if (student.getRole() != ROLE_ID)
			{
				throw new BadRoleException("User is not a student");
			}
		}
		return Optional.ofNullable(student);
	}
	
	/*
	public MyAppUser getByIteration(Set<Iteration> set) 
	{
		MyAppUser student = null;
		Optional<MyAppUser> studentOptional = MyAppUserRepository.findById(set);
		System.out.println(set);
		if(studentOptional.isPresent()) 
		{
			student = studentOptional.get();
		}
		else 
		{
			System.out.println("Student not found 404");
		}
		System.out.println(student.getFirstName());
		return student;
	}
	*/

	public MyAppUser updateById(Student student)
	{
		if (myAppUserRepository.existsById(student.getId()))
		{
			MyAppUser user = myAppUserRepository.findOneById(student.getId())
					.orElseThrow(() -> new UserNotFoundException("User not found"));
			user.setFirstName(student.getFirstName());
			user.setLastName(student.getLastName());
			myAppUserRepository.save(user);
			return user;
		} 
		else
		{
			return null;
		}
	}

	public MyAppUser editStudent(MyAppUser student)
	{
		if (myAppUserRepository.existsById(student.getId()))
		{
			MyAppUser user = myAppUserRepository.findOneById(student.getId())
					.orElseThrow(() -> new ResourceNotFoundException("not found"));
			if (student.getFirstName() != null && !student.getFirstName().isEmpty())
			{
				user.setFirstName(student.getFirstName());
			}
			if (student.getLastName() != null && !student.getLastName().isEmpty())
			{
				user.setLastName(student.getLastName());
			}
			myAppUserRepository.save(user);
			return user;
		} 
		else
		{
			return null;
		}
	}

	public String getFullNameById(String studentId)
	{
		Student student = getById(studentId);
		return student.getLastName() + ", " + student.getFirstName();
	}

	public String getFirstNameById(String studentId)
	{
		return getById(studentId).getFirstName();
	}

	public String getLastNameById(String studentId)
	{
		return getById(studentId).getLastName();
	}

	public Teacher addTeacher(Teacher teacher)
	{
		return myAppUserRepository.save(teacher);
	}

	public List<Student> getStudents()
	{
		return (List<Student>) myAppUserRepository.findByRole(MyAppUser.Role.STUDENT);
	}

	/*
	MyAppUser findUserByEmail(final String email)
	{ 
		return myAppUserRepository.findByEmail(email); 
	}

	createPasswordResetTokenForUser(MyAppUser user, String token)
	{
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken); 
	}
	
	private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, MyAppUser user) 
	{ 
		String url = contextPath + "/user/changePassword?id=" + user.getId() + 
				"&token=" + token; String message = messages.getMessage("message.resetPassword", null, locale); 
		return constructEmail("Reset Password", message + " \r\n" + url, user); 
	}
	
	private SimpleMailMessage constructEmail(String subject, String body, MyAppUser user) 
	{ 
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject); email.setText(body); 
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email")); 
		return email; 
	}

	public static void changeUserPassword(MyAppUser user, String password)
	{
		user.setPassword(passwordEncoder.encode(password));
		myAppUserRepository.save(user); 
	}
	*/

}