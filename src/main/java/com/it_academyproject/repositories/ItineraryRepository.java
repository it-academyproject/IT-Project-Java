package com.it_academyproject.repositories;

import com.it_academyproject.domains.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer>
{
	Itinerary findOneById(Integer id);
}