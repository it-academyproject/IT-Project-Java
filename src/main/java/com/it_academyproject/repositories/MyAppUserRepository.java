package com.it_academyproject.repositories;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.MyAppUser.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, String>{

	public ArrayList<MyAppUser> findByFirstName(String firstName);
	public ArrayList<MyAppUser> findByLastName(String lastName);
	public List<MyAppUser> findByGender(char gender);
	public List<? extends MyAppUser> findByFirstNameAndRole(String firstName, Role role);
	public List<? extends MyAppUser> findByLastNameAndRole(String lastName, Role role);
	public List<? extends MyAppUser> findByRole(Role role);



	MyAppUser findOneById(String id );
	MyAppUser findOneByIdAndRole(String id, Role role);

	MyAppUser findByEmail(String email);

	List<MyAppUser> findByFirstNameAndLastName (String name , String lastName );
	List<? extends MyAppUser> findByGenderAndRole (char gender, Role role);

	@Query("UPDATE MyAppUser u SET u.lastLogin=:lastLogin WHERE u.email = ?#{ principal?.email }")
	@Modifying
	@Transactional
	public void updateLastLogin (@Param("lastLogin") Date lastLogin);

	@Query(value="SELECT u from MyAppUser u WHERE u.firstName like '%:name%'")
	List<MyAppUser> findUserByNameLike(@Param("name") String name);


	boolean existsByIdAndRole(String id, Role role);
}
