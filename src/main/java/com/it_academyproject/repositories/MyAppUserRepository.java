package com.it_academyproject.repositories;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.MyAppUser.Role;
import com.it_academyproject.domains.StatusExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, String>{

	ArrayList<MyAppUser> findByFirstName(String firstName);
	List<? extends MyAppUser> findByFirstNameAndRole(String firstName, Role role);
	List<? extends MyAppUser> findByLastNameAndRole(String lastName, Role role);
	List<? extends MyAppUser> findByRole(Role role);

	Optional <MyAppUser> findOneById(String id );
	MyAppUser findOneByIdAndRole(String id, Role role);
	public ArrayList<MyAppUser> findByLastName(String lastName);
	public List<MyAppUser> findByGender(char gender);
	//public ArrayList<MyAppUser> findByRoleId(int roleId);
	
	public String getUserById(String id);

	
	MyAppUser findUserById(String id );


	MyAppUser findByEmail(String email);

	List<MyAppUser> findByFirstNameAndLastName (String name , String lastName );
	List<? extends MyAppUser> findByGenderAndRole (char gender, Role role);
	boolean existsByIdAndRole(String id, Role role);

	@Query("UPDATE MyAppUser u SET u.lastLogin=:lastLogin WHERE u.email = ?#{ principal?.email }")
	@Modifying
	@Transactional
	void updateLastLogin (@Param("lastLogin") Date lastLogin);

	@Query(value="SELECT u from MyAppUser u WHERE u.firstName like '%:name%'")
	List<MyAppUser> findUserByNameLike(@Param("name") String name);

	//List<MyAppUser> findByIterations_IterationName(String iterationName);

	@Query(value = "SELECT u.* FROM user_exercise ue " +
			"JOIN status_exercise se ON ue.status_id = se.id " +
			"JOIN users u ON ue.student_id = u.id " +
			"WHERE se.name=:statusName AND TIMEDIFF(:datte, ue.date_status) < 30", nativeQuery = true )
	List<MyAppUser> bla(String statusName, String datte);


}
