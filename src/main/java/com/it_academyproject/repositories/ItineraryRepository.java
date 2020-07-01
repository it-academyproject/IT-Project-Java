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

    @Query(value = "SELECT i.name AS itinerary_name, COUNT(f.student_id) as num_students " +
                    "FROM itinerary i " +
                    "JOIN (SELECT i.id AS inner_id, ue.student_id AS student_id, u.last_name AS last_name " +
                            "FROM user_exercise ue " +
                            "JOIN exercise e ON ue.exercise_id=e.id " +
                            "JOIN itinerary i ON e.itinerary_id=i.id " +
                            "JOIN users u ON ue.student_id=u.id " +
                            "WHERE ue.status_id!=:statusId AND TIMESTAMPDIFF( DAY, ue.date_status,:destDate) > 8 " +
                            "GROUP BY i.id, student_id) f ON i.id=f.inner_id " +
                    "GROUP BY i.name", nativeQuery = true )
    List<DTOItinerariesLastDelivery> itinerarys_deliveries(Integer statusId, LocalDateTime destDate);
}
