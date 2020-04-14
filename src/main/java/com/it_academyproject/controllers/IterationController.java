package com.it_academyproject.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.tools.View;

@RestController
public class IterationController {
	
	@Autowired
	IterationRepository iterationRepository;
	
	 
	@Autowired
	private   MyAppUserRepository myAppUserRepository;
	

	@GetMapping("/api/iterations")
	public List<Iteration> getAllIterations() {
		List<Iteration> it = iterationRepository.findAll();
		return it;
	}
	
	
	@PostMapping("/api/iterations")                                  
	public Iteration createIteration(@Valid @RequestBody Iteration iteration) throws Exception {
		boolean isFound=false;
		List <Iteration> checkIteration=  iterationRepository.findAll();
		
		
		for (Iteration one : checkIteration) {
			
			
			
			
			if ((iteration.getName().equals(one.getName()) )) {
				isFound=true;
						
							
			}
		}
			
		//if the new iteration is found, an exception will be thrown.
		if (isFound) {
			throw new Exception("This iteration is already set up");
		}else{
			return iterationRepository.save(iteration);
		}
		
	

}
}
