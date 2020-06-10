package com.it_academyproject.repositories;

import com.it_academyproject.controllers.DTOs.itineraryDTOs.DTOItinerariesLastDelivery;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsLastDelivery;
import com.it_academyproject.domains.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer>
{
    Itinerary findOneById(Integer id );

    @Query(value = "SELECT i.*, COUNT(ue.status_id) AS numStudents " +
                    "FROM itinerary i " +
                    "JOIN exercise e ON i.id=e.itinerary_id " +
                    "JOIN user_exercise ue ON ue.exercise_id=e.id " +
                    "JOIN status_exercise se ON se.id=ue.status_id " +
                    "WHERE se.id!=:statusId AND TIMESTAMPDIFF( DAY, ue.date_status,:destDate) > 8", nativeQuery = true )
    List<DTOItinerariesLastDelivery> itinerarys_deliveries(Integer statusId, LocalDateTime destDate);

}
