
package com.it_academyproject.repositories;

import com.it_academyproject.domains.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer>
{
    public List<Course> findByEndDate(Date endDate );
    public List<Course> findByUserStudent(Student user );
    public List<Course> findByItinerary(Itinerary itinerary);
    public List<Course> findByItineraryAndEndDate( Itinerary itinerary , Date date);
    List<Course> findByTeacher(Teacher teacher);
}
