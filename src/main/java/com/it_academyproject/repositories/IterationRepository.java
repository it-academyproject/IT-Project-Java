package com.it_academyproject.repositories;

import com.it_academyproject.domains.Iteration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IterationRepository extends JpaRepository<Iteration, Long> {

	
//	List<Iteration> findAllByUsers(MyAppUser user);
//	List<Iteration> findByUsers_Id(String id);
	Optional<Iteration> findById(Long iterationId);
	List<Iteration> findAll();
	//Iteration save(Optional<Iteration> iteration);
	Iteration save(Optional<Iteration> iteration);


}
