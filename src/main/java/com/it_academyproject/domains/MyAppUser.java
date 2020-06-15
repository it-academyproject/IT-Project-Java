
package com.it_academyproject.domains;


import com.fasterxml.jackson.annotation.JsonFormat;
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
public abstract class MyAppUser {

	// Order according to former implementation
	// This order helps SQL data import (IT role=0, Student=1...)
	public enum Role {
		IT, STUDENT, TEACHER, ADMIN
	}

	
	@Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView({View.Summary.class, View.ShortDetails.class})
	private String id;

	
	@JsonView({View.Summary.class, View.ShortDetails.class})
	private String firstName;
	
	@JsonView({View.Summary.class, View.ShortDetails.class})
	private String lastName;

	@JsonView(View.SummaryWithOthers.class)
	private String email;

	@JsonView(View.SummaryWithOthers.class)
	private char gender;

	private String password;
	private boolean enabled;
	private Date lastLogin;

	private Role role;
	
	@JsonView(View.Summary.class)
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date lastClassAttendance;

	
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
	}
	
	public MyAppUser(String firstName, String lastName, String email, char gender,
					 String password, boolean enabled, Role role) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
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

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getLastClassAttendance() {
		return lastClassAttendance;
	}

	public void setLastClassAttendance(Date lastClassAttendance) {
		this.lastClassAttendance = lastClassAttendance;
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

	@Override
	public String toString() {
		return "MyAppUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", gender=" + gender + ", password="
				+ password + ", enabled=" + enabled + ", lastLogin=" + lastLogin + ", role=" + role
				+ ", lastClassAttendance=" + lastClassAttendance + ", absences=" + absences + ", totalAbsences="
				+ totalAbsences + ", courses=" + courses + ", emails=" + emails + ", userIterations=" + userIterations
				+ "]";
	}
	
	

	//	public Set<Iteration> getIterations() {
//		return iterations;
//	}
//
//	public void setIterations(Iteration iteration) {
//		this.iterations.add(iteration) ;
//	}
//	
	
	

	/*	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", password=" + password
				+ ", enabled=" + enabled + "]";
	}	*/

}
// {"id":"030027e8-3bd2-431d-b57b-2f3b60aed01b"}