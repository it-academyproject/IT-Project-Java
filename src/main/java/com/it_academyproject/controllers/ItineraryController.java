package com.it_academyproject.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.it_academyproject.controllers.DTOs.itineraryDTOs.DTOItinerariesLastDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.Project;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.ItineraryRepository;

@RestController
public class ItineraryController {
	
	@Autowired
	ItineraryRepository itineraryRepository;
	
	@GetMapping("/api/itineraries")
	public List<Itinerary> getAllItinerary() {
		List<Itinerary> it = itineraryRepository.findAll();
		return it;
	}
	
	@PostMapping("/api/itineraries")                                  
	public Itinerary createItinerary(@Valid @RequestBody Itinerary itinerary) throws Exception {
		boolean isFound=false;
		List <Itinerary> checkItinerary=  itineraryRepository.findAll();
		
		
		//foreach loop to check if the new itinerary is already in the repository
		for (Itinerary one : checkItinerary) {
			
			
			
			if ((itinerary.getName().equals(one.getName()) )) {
				isFound=true;
						
							
			}
		}
			
		//if the new itinerary is found, an exception will be thrown.
		if (isFound) {
			throw new Exception("This itinerary is already set up");
		}else{
			return itineraryRepository.save(itinerary);
		}
	
	}

}
