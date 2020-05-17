package com.it_academyproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.domains.Iteration;
import com.it_academyproject.repositories.IterationRepository;

@RestController
public class IterationController
{
	@Autowired
	IterationRepository iterationRepository;

	// -------------------- -------------------- //
	
	@PostMapping("/api/iterations")
	public Iteration createIteration(@Valid @RequestBody Iteration iteration) throws Exception
	{
		boolean isFound = false;
		List<Iteration> checkIteration = iterationRepository.findAll();
		
		for (Iteration one : checkIteration)
		{
			if ((iteration.getName().equals(one.getName())))
			{
				isFound = true;
			}
		}
		
		if (isFound)
		{
			throw new Exception("This iteration is already set up");
		} 
		else
		{
			return iterationRepository.save(iteration);
		}
	}
	
	@GetMapping("/api/iterations")
	public List<Iteration> getAllIterations()
	{
		List<Iteration> iteration = iterationRepository.findAll();
		return iteration;
	}
	
}