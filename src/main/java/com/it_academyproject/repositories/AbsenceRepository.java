package com.it_academyproject.repositories;

import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer>
{
    //student id y date missing
    List<Absence> findByUserStudentAndDateMissing (Student userStudent , Date dateMissing );
   
}
