
package com.it_academyproject.domains;


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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.exceptions.EmptyFieldException;
import com.it_academyproject.tools.View;



@Entity
@Table(name="users")
public class MyAppUser {

	//@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
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

	@JsonView(View.SummaryWithOthers.class)
	private int age;

	@JsonView(View.SummaryWithOthers.class)
	private String portrait;


	@JsonView(View.Summary.class)
	@ManyToOne
	private Seat seat;

	private String password;
	private boolean enabled;
	private Date lastLogin;


	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="rol_id")
	private Role role;

	@OneToMany (targetEntity = Absence.class, cascade = CascadeType.ALL)
	private List <Absence> absences = new ArrayList <Absence>();
	@OneToMany (targetEntity = Course.class, cascade = CascadeType.ALL)
	@JsonView(View.Summary.class)
	public List <Course> courses = new ArrayList <Course>();
	@OneToMany (targetEntity = UserExercice.class, cascade = CascadeType.ALL)
	private List <UserExercice> userExercices = new ArrayList <UserExercice>();
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

	// Necessary for testing purposes
	public void setId(String id) {
		this.id = id;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPortrait() {
		return portrait;
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
		return role;
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

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Course> getCourses() {
		return courses;
	}

	//	public Set<Iteration> getIterations() {
//		return iterations;
//	}
//
//	public void setIterations(Iteration iteration) {
//		this.iterations.add(iteration) ;
//	}
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


}