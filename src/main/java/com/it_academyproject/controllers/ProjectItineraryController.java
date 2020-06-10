package com.it_academyproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.Project;
import com.it_academyproject.domains.ProjectItinerary;
import com.it_academyproject.domains.UserIteration;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.ProjectItineraryRepository;
import com.it_academyproject.repositories.ProjectRepository;
import com.it_academyproject.repositories.UserIterationRepository;
import com.it_academyproject.services.MyAppUserService;

@RestController
public class ProjectItineraryController {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ItineraryRepository itineraryRepository;
	
	@Autowired
	ProjectItineraryRepository projectItineraryRepository;
	
	
	@GetMapping("api/projectitinerary")
	public List<ProjectItinerary> getAllPI(){
		return projectItineraryRepository.findAll();
	}
	

	@PostMapping("/api/projectitinerary")
	public ProjectItinerary createPI(@RequestBody ProjectItinerary PI) {
		//UI only contains project_id and itinerary_id from Postman
		
		Project project = PI.getProject();
		Itinerary itinerary = PI.getItinerary();
		Project trueProject = projectRepository.findById(project.getId())
				.orElseThrow(() -> new ResourceNotFoundException("project not found"));
		
				
		Itinerary trueItinerary = itineraryRepository.findById(itinerary.getId())
			.orElseThrow(() -> new ResourceNotFoundException("itinerary not found"));
		
		
		//Set the project and itinerary with all their data into UI:
		PI.setItinerary(trueItinerary);
		PI.setProject(trueProject);
		ProjectItinerary projectItinerary = new ProjectItinerary(PI);
		
		
		return projectItineraryRepository.save(projectItinerary);
		

		
	}

}

