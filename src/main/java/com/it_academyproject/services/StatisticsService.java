package com.it_academyproject.services;

import com.google.gson.Gson;
import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsFinishXDays;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class StatisticsService {

    // Absences to compare to when searching students per absences
    private static final Integer MAX_ABSENCES = 8;
    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ItineraryRepository itineraryRepository;


    public Map<String, Integer> perItinerary() {

        Map<String, Integer> result = new HashMap<>();

        for (Itinerary itinerary : itineraryRepository.findAll())
            result.put(itinerary.getName(), getNum_students(itinerary));

        return result;
    }

    private int getNum_students(Itinerary itinerary) {
        return courseRepository.findByItinerary(itinerary).size();
    }

    // Returns a map of <StudentId, #absences> of the students with MAX_ABSENCES or more
    public Map<String, Integer> perAbsence() {

        List<Absence> absences = absenceRepository.findAll();
        Map<String, Integer> absencesPerStudent = new HashMap<>();

        String studentId;
        // Fill the map with the absences per student
        for (Absence absence : absences) {
            studentId = absence.getUserStudent().getId();
            if (absencesPerStudent.putIfAbsent(studentId, 1) != null)
                absencesPerStudent.replace(studentId, absencesPerStudent.get(studentId) + 1);
        }

        // Iterate the map and remove students with less than MAX_ABSENCES absences
        // Not possible to remove an entry from a map that is being iterated, copy needed
        Map<String, Integer> copyMap = new HashMap(absencesPerStudent);
        for (Map.Entry<String, Integer> entry : copyMap.entrySet()) {
            if (entry.getValue() < MAX_ABSENCES) {
                absencesPerStudent.remove(entry.getKey());
            }
        }

        return absencesPerStudent;
    }

    public List<DTOStudentsFinishXDays> finishInXdays(int xDays){

        Date currentDate = Calendar.getInstance().getTime();
        List<DTOStudentsFinishXDays> dtos = new ArrayList<DTOStudentsFinishXDays>();
        List<Course> finishInXDays = courseRepository.findAll();

        for (int i = 0; i < finishInXDays.size(); i++) {
            if (finishInXDays.get(i).getEndDate() != null) {
                Long days = (Long) ((finishInXDays.get(i).getEndDate().getTime() - currentDate.getTime()) / 86400000);
                if (days <= xDays) {
                    DTOStudentsFinishXDays dto = new DTOStudentsFinishXDays();
                    dto.setId( finishInXDays.get(i).getUserStudent().getId() );
                    dto.setFirstName( finishInXDays.get(i).getUserStudent().getFirstName() );
                    dto.setLastName( finishInXDays.get(i).getUserStudent().getLastName() );
                    dto.setDays( days );
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

}


    /*********************************************** NEVER USED METHODS *****************************

     public static ArrayList<String> peopleByItineraryMethod(String itinerary, String numberOfPeople) {
     ArrayList<String> itineraryObject = new ArrayList<>();
     itineraryObject.add(itinerary);
     itineraryObject.add(numberOfPeople);
     return itineraryObject;
     }
     public static ArrayList<String> peopleByGenderMethod (Character typeOfGender, String numberOfUsers){
     ArrayList<String> peopleByGenderList = new ArrayList<String>();
     peopleByGenderList.add(typeOfGender.toString());
     peopleByGenderList.add(numberOfUsers);
     return peopleByGenderList;
     }
     public static ArrayList<String> AbsenceMethod (String name, String absence){
     ArrayList<String> absenceList = new ArrayList<String>();
     absenceList.add(name);
     absenceList.add(absence);
     return absenceList;
     }
     public static ArrayList<String> endDayMethod (String name, String daysLeft){
     ArrayList<String> endDayList = new ArrayList<String>();
     endDayList.add(name);
     endDayList.add(daysLeft);
     return endDayList;
     }

    //Never used method in dev branch on 14th April 2020
    public List<Student> getAllActiveStudents ( ) {
        List<Course> courseList = courseRepository.findByEndDate( null );
        List<Student> activeStudents = new ArrayList<>();
        Course course;
        Student student1;
        for (int i = 0; i < courseList.size() ; i++)
        {
            course = courseList.get(i);
            MyAppUser student = course.getUserStudent();
            myAppUser = myAppUserRepository.findOneById( student.getId() )
                    .orElseThrow(() -> new ResourceNotFoundException("not found"));
            if ( myAppUser != null )
            {
                activeStudents.add( student1 );
            }
            else
            {
                throw (new UserNotFoundException( student.getId() ));
            }
        }
        return (activeStudents);
    }

    // Never used method in b-30 branch on 14th April 2020
    public List<Student> getAllActiveStudents ( ) {
        List<Course> courseList = courseRepository.findByEndDate( null );
        List<Student> activeStudents = new ArrayList<>();
        Course course;
        Student student1;
        for (int i = 0; i < courseList.size() ; i++)
        {
            course = courseList.get(i);
            Student student = course.getUserStudent();
            student1 = studentService.findOneById( student.getId() );
            if ( student1 != null )
            {
                activeStudents.add( student1 );
            }
            else
            {
                throw (new UserNotFoundException( student.getId() ));
            }
        }
        return (activeStudents);
    }
*/