package com.it_academyproject.repositories;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.MyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer>
{

    //by student id y date missing
    List<Absence> findByUserStudentAndDateMissing (MyAppUser userStudent , Date dateMissing );
    
    //Find List by student id 
    List<Absence> findListByUserStudent(MyAppUser userStudent);
    
    //Find Absence by user
    Absence findByUserStudent(MyAppUser userStudent);
    
    //Find Absence by user id
	List<Absence> findOneById(String id);
    
    //by absence id
//    Absence findByAbsenceId (int id);
    

}
