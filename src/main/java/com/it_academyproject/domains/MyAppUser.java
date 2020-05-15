
package com.it_academyproject.domains;


<<<<<<< HEAD
<<<<<<< HEAD
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

=======
import com.fasterxml.jackson.annotation.JsonFormat;
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
import com.fasterxml.jackson.annotation.JsonFormat;
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.exceptions.EmptyFieldException;
import com.it_academyproject.tools.View;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "subclass", discriminatorType = DiscriminatorType.STRING)
@Table(name="users")
<<<<<<< HEAD
<<<<<<< HEAD
public class MyAppUser {

	//@GeneratedValue(strategy=GenerationType.IDENTITY)	
=======
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
public abstract class MyAppUser {

	// Order according to former implementation
	// This order helps SQL data import (IT role=0, Student=1...)
	public enum Role {
		IT, STUDENT, TEACHER, ADMIN
	}

	
<<<<<<< HEAD
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
	@Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView({View.Summary.class, View.ShortDetails.class})
	private String id;

<<<<<<< HEAD
<<<<<<< HEAD
=======
	
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
	
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
	@JsonView({View.Summary.class, View.ShortDetails.class})
	private String firstName;
	
	@JsonView({View.Summary.class, View.ShortDetails.class})
	private String lastName;

	@JsonView(View.SummaryWithOthers.class)
	private String email;

	@JsonView(View.SummaryWithOthers.class)
	private char gender;

	@JsonView(View.SummaryWithOthers.class)
<<<<<<< HEAD
<<<<<<< HEAD
	private int age;
=======
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date birthdate;
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date birthdate;
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6

	@JsonView(View.SummaryWithOthers.class)
	private String portrait;

	private String password;
	private boolean enabled;
	private Date lastLogin;

<<<<<<< HEAD
<<<<<<< HEAD

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="rol_id")
	private Role role;

=======
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
	private Role role;
	
	@JsonView(View.Summary.class)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date lastClassAttendance;

	
<<<<<<< HEAD
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
	@OneToMany (targetEntity = Absence.class, cascade = CascadeType.ALL)
	private List <Absence> absences = new ArrayList <Absence>();
	@JsonView(View.Summary.class)
	private int totalAbsences;
	@OneToMany (targetEntity = Course.class, cascade = CascadeType.ALL)
	@JsonView(View.Summary.class)
	private List <Course> courses = new ArrayList <Course>();
	@OneToMany (targetEntity = Emails.class, cascade = CascadeType.ALL)
	private List <Emails> emails = new ArrayList <Emails>();

//	@ManyToMany(fetch = FetchType.LAZY,
//            cascade = {
//                CascadeType.PERSIST,
//                CascadeType.MERGE
//            })
//    @JoinTable(name = "user_iteration",
//            joinColumns = { @JoinColumn(name = "my_app_user_id") },
//            inverseJoinColumns = { @JoinColumn(name = "iteration_id") })
//	@JsonIgnore
//	private Set<Iteration> iterations = new HashSet<>();
	
	@OneToMany(mappedBy="myAppUser")
	@JsonIgnore
	Set<UserIteration> userIterations;
	
	
	public MyAppUser() {
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
	}
	
	public MyAppUser(String firstName, String lastName, String email, char gender,
					 String portrait, String password, boolean enabled, Role role) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.portrait = portrait;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		
	}
	
	 public MyAppUser(String email, String password) throws EmptyFieldException
	    {
	        if ((email != "")&&(password!=""))
	        {
	            this.email = email;
	            this.password = password;
	            this.lastLogin = new Date();
	            this.enabled = true;
	        }
	        else if (email == "")
	        {
	            throw (new EmptyFieldException("email"));
	        }
	        else if ( password == "" )
	        {
	            throw (new EmptyFieldException("password"));
	        }
	    }

	public String getId() {
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id.toString();
	}
	public void setId()
	{
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getGender() {
		return gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPortrait() {
		return portrait;
	}

	public Date getLastClassAttendance() {
		return lastClassAttendance;
	}

	public void setLastClassAttendance(Date lastClassAttendance) {
		this.lastClassAttendance = lastClassAttendance;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Course> getCourses() {
		return courses;
	}



	public List<Absence> getAbsences() {
		return absences;
	}
	
	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public void setTotalAbsences(int totalAbsences) {
		this.totalAbsences = totalAbsences;
	}

<<<<<<< HEAD
<<<<<<< HEAD
	public List<Course> getCourses() {
		return courses;
	}
=======
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
	@Override
	public String toString() {
		return "MyAppUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", gender=" + gender + ", birthdate=" + birthdate + ", portrait=" + portrait + ", password="
				+ password + ", enabled=" + enabled + ", lastLogin=" + lastLogin + ", role=" + role
				+ ", lastClassAttendance=" + lastClassAttendance + ", absences=" + absences + ", totalAbsences="
				+ totalAbsences + ", courses=" + courses + ", emails=" + emails + ", userIterations=" + userIterations
				+ "]";
	}
	
	
<<<<<<< HEAD
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6

	//	public Set<Iteration> getIterations() {
//		return iterations;
//	}
//
//	public void setIterations(Iteration iteration) {
//		this.iterations.add(iteration) ;
//	}
<<<<<<< HEAD
<<<<<<< HEAD
//


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MyAppUser)) return false;
		MyAppUser myAppUser = (MyAppUser) o;
		return getGender() == myAppUser.getGender() &&
				getAge() == myAppUser.getAge() &&
				isEnabled() == myAppUser.isEnabled() &&
				getId().equals(myAppUser.getId()) &&
				Objects.equals(getFirstName(), myAppUser.getFirstName()) &&
				Objects.equals(getLastName(), myAppUser.getLastName()) &&
				Objects.equals(getEmail(), myAppUser.getEmail()) &&
				Objects.equals(getPortrait(), myAppUser.getPortrait()) &&
				Objects.equals(getSeat(), myAppUser.getSeat()) &&
				Objects.equals(getPassword(), myAppUser.getPassword()) &&
				Objects.equals(getLastLogin(), myAppUser.getLastLogin()) &&
				Objects.equals(getRole(), myAppUser.getRole()) &&
				Objects.equals(absences, myAppUser.absences) &&
				Objects.equals(getCourses(), myAppUser.getCourses()) &&
				Objects.equals(userExercices, myAppUser.userExercices) &&
				Objects.equals(emails, myAppUser.emails) &&
				Objects.equals(userIterations, myAppUser.userIterations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getGender(), getAge(), getPortrait(), getSeat(), getPassword(), isEnabled(), getLastLogin(), getRole(), absences, getCourses(), userExercices, emails, userIterations);
	}

	@Override
	public String toString() {
		return "MyAppUser{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", gender=" + gender +
				", age=" + age +
				", portrait='" + portrait + '\'' +
				", seat=" + seat +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", lastLogin=" + lastLogin +
				", role=" + role +
				", absences=" + absences +
				", courses=" + courses +
				", userExercices=" + userExercices +
				", emails=" + emails +
				", userIterations=" + userIterations +
				'}';
	}

=======
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6
//	
	
	

	/*	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", portrait=" + portrait + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}	*/
<<<<<<< HEAD
>>>>>>> a67427bc53d556b5121a327403091f38ea49f8eb
=======
>>>>>>> 23d29a39df85afb9934302bc76d005636245cbb6

}
// {"id":"030027e8-3bd2-431d-b57b-2f3b60aed01b"}