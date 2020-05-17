package com.it_academyproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it_academyproject.domains.UserIteration;

public interface UserIterationRepository extends JpaRepository<UserIteration, Long>
{

}
