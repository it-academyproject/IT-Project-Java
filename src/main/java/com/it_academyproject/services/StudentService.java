package com.it_academyproject.services;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Student;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.MyAppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{
	@Autowired
	MyAppUserRepository myAppUserRepository;

	@Autowired
	CourseService courseService;

	@Autowired
	UserExerciseService userExerciseService;

	// -------------------- -------------------- //
	
	// get all students
	@SuppressWarnings("unchecked")
	public List<Student> getAllStudents()
	{
		return updateStudentCourses((List<Student>) myAppUserRepository.findByRole(MyAppUser.Role.STUDENT));
	}

	private List<Student> updateStudentCourses(List<Student> students)
	{
		for (Student student : students)
		{
			student.setCourses(courseService.findByUserStudent(student));
			student.setLastDeliveredExercise(userExerciseService.getLastDeliveredExerciseDate(student));
		}
		return students;
	}

	// get students by name
	@SuppressWarnings("unchecked")
	public List<Student> getByName(String firstName)
	{
		return (List<Student>) myAppUserRepository.findByFirstNameAndRole(firstName, MyAppUser.Role.STUDENT);
	}

	// get students by lastName
	@SuppressWarnings("unchecked")
	public List<Student> getBySurname(String lastName)
	{
		return (List<Student>) myAppUserRepository.findByLastNameAndRole(lastName, MyAppUser.Role.STUDENT);
	}

	// get students by Id
	public Student getById(String id)
	{
		return (Student) myAppUserRepository.findOneByIdAndRole(id, MyAppUser.Role.STUDENT);
	}

	// get students by gender
	public int studentsByGender(char gender)
	{
		return myAppUserRepository.findByGenderAndRole(gender, MyAppUser.Role.STUDENT).size();
	}

	// Return full name given an id. Format "lastName, Name".
	public String getFullNameById(String studentId)
	{
		MyAppUser student = getById(studentId);
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

	public Student findOneById(String id)
	{
		return (Student) myAppUserRepository.findOneById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	public Student addStudent(Student student)
	{
		return myAppUserRepository.save(student);
	}

	public Student editStudent(Student student)
	{

		if (myAppUserRepository.existsByIdAndRole(student.getId(), MyAppUser.Role.STUDENT))
		{
			Student repoStudent = (Student) myAppUserRepository.findOneById(student.getId())
					.orElseThrow(() -> new UserNotFoundException("User not found"));
			if (student.getFirstName() != null && !student.getFirstName().isEmpty())
			{
				repoStudent.setFirstName(student.getFirstName());
			}
			if (student.getLastName() != null && !student.getLastName().isEmpty())
			{
				repoStudent.setLastName(student.getLastName());
			}
			// Update enabled if needed
			if (student.isEnabled() != repoStudent.isEnabled())
			{
				repoStudent.setEnabled(student.isEnabled());
			}
			myAppUserRepository.save(repoStudent);
			return repoStudent;
		} else
		{
			return null;
		}
	}

	/*
	 * // find the user by the email passed to the repository static public
	 * MyAppUser findUserByEmail(final String email){ return
	 * myAppUserRepository.findByEmail(email); }
	 * 
	 * // Stores the user token generated in the controller public static void
	 * createPasswordResetTokenForUser(MyAppUser user, String token){
	 * PasswordResetToken myToken = new PasswordResetToken(token, user);
	 * passwordTokenRepository.save(myToken); }
	 * 
	 * private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale
	 * locale, String token, MyAppUser user) { String url = contextPath +
	 * "/user/changePassword?id=" + user.getId() + "&token=" + token; String message
	 * = messages.getMessage("message.resetPassword", null, locale); return
	 * constructEmail("Reset Password", message + " \r\n" + url, user); }
	 * 
	 * private SimpleMailMessage constructEmail(String subject, String body,
	 * MyAppUser user) { SimpleMailMessage email = new SimpleMailMessage();
	 * email.setSubject(subject); email.setText(body); email.setTo(user.getEmail());
	 * email.setFrom(env.getProperty("support.email")); return email; }
	 * 
	 * public static void changeUserPassword(MyAppUser user, String password){
	 * user.setPassword(passwordEncoder.encode(password));
	 * myAppUserRepository.save(user); }
	 */

}