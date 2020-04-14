package com.it_academyproject.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserIteration;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.repositories.UserIterationRepository;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.tools.View;

@RestController
public class UserIterationController {
	
	@Autowired
	MyAppUserService myAppUserService;
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	@Autowired
	IterationRepository iterationRepository;
	
	@Autowired
	UserIterationRepository userIterationRepository;
	
	@GetMapping("api/useriteration")
	public List<UserIteration> getAllUI(){
		return userIterationRepository.findAll();
	}
	
	@PostMapping("/api/useriteration")
	public UserIteration createUI(@RequestBody UserIteration UI) {
		//UI only contains user_id and iteration_id from Postman
		Iteration iteration = UI.getIteration();
		MyAppUser myAppUser = UI.getMyAppUser();
		Iteration trueIteration = iterationRepository.findById(iteration.getId())
				.orElseThrow(() -> new ResourceNotFoundException("iteration not found"));
		
				
		MyAppUser trueUser = myAppUserRepository.findOneById(myAppUser.getId())
			.orElseThrow(() -> new ResourceNotFoundException("student not found"));
		
		//Set the iteration and user with all their data into UI:
		UI.setIteration(trueIteration);
		UI.setMyAppUser(trueUser);
		UserIteration userIteration = new UserIteration(UI);
		
		
		return userIterationRepository.save(userIteration);
		
		
	}

}
