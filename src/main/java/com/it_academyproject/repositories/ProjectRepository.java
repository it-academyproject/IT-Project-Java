package com.it_academyproject.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.it_academyproject.domains.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findAll();

	@Query(value = "SELECT p.id, p.name, p.description, COUNT(pi.project_id) AS num_itineraries " +
			"		FROM project p " +
			"		INNER JOIN project_iterations pi " +
			"		ON p.id = pi.project_id " +
			"		GROUP BY p.id", nativeQuery = true)
	List<Map<String, Object>> findAllByIterations();

}
