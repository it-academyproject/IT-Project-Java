package com.it_academyproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.ResourceNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

@Service
public class MyAppUserService {
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	@Autowired
	IterationRepository iterationRepository;
	
	//getAll
	public List<MyAppUser> getAllStudents(){
		int studentRoleId=1;
		return myAppUserRepository.findByRoleId(studentRoleId);
	}

	//get by name
	public ArrayList<MyAppUser> getByName(String firstName){
		return myAppUserRepository.findByFirstName(firstName);
	}

	//get by surName
	public ArrayList<MyAppUser> getBySurname(String lastName) {
		return myAppUserRepository.findByLastName(lastName);
	}

	//get by dni
	public MyAppUser getByDni(String idDocument) {
		return myAppUserRepository.findByIdDocument(idDocument);
	}

	//get by Id
	public MyAppUser getById(String id) {
		MyAppUser student = null;
		Optional<MyAppUser> studentOptional = myAppUserRepository.findById(id);
		System.out.println(id);
		if(studentOptional.isPresent()) {
			student = studentOptional.get();
		}else {
			System.out.println("Student not found 404");
		}
		System.out.println(student.getFirstName());
		return student;
	}
	
	//get by Iteration
//		public MyAppUser getByIteration(Set<Iteration> set) {
//			MyAppUser student = null;
//			Optional<MyAppUser> studentOptional = MyAppUserRepository.findById(set);
//			System.out.println(set);
//			if(studentOptional.isPresent()) {
//				student = studentOptional.get();
//			}else {
//				System.out.println("Student not found 404");
//			}
//			System.out.println(student.getFirstName());
//			return student;
//		}
	
	// Put - Edit by dni
		public MyAppUser editGetByDni(MyAppUser student) {
			
			if(myAppUserRepository.existsById(student.getId())) {
			MyAppUser user = myAppUserRepository.findOneById(student.getId())
					.orElseThrow(() -> new ResourceNotFoundException("not found"));
			user.setFirstName(student.getFirstName());
			user.setLastName(student.getLastName());
			System.out.println(user.getRole().getId());
			myAppUserRepository.save(user);
			
			 return user;
			 }else {return null;}
		}
}