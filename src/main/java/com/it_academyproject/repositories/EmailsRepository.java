
package com.it_academyproject.repositories;



import java.util.List;

import com.it_academyproject.domains.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.it_academyproject.domains.Emails;
import com.it_academyproject.domains.MyAppUser;


@Repository
public interface EmailsRepository extends CrudRepository<Emails, Integer> {
	public List<Emails> findByUserStudent(Student user);
	

}