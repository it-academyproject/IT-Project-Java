package com.it_academyproject.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it_academyproject.domains.MyAppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MyAppUserRepository extends JpaRepository<MyAppUser, String>{

	public ArrayList<MyAppUser> findByFirstName(String firstName);
	public ArrayList<MyAppUser> findByLastName(String lastName);
	public List<MyAppUser> findByGender(char gender);
	public ArrayList<MyAppUser> findByRoleId(int roleId);
	

	Optional <MyAppUser> findOneById(String id );

	MyAppUser findByEmail(String email);

	List<MyAppUser> findByFirstNameAndLastName (String name , String lastName );

	@Query("UPDATE MyAppUser u SET u.lastLogin=:lastLogin WHERE u.email = ?#{ principal?.email }")
	@Modifying
	@Transactional
	public void updateLastLogin (@Param("lastLogin") Date lastLogin);

	@Query(value="SELECT u from MyAppUser u WHERE u.firstName like '%:name%'")
	List<MyAppUser> findUserByNameLike(@Param("name") String name);
	
	//List<MyAppUser> findByIterations_IterationName(String iterationName);
}
