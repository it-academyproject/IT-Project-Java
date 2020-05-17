package com.it_academyproject.jwt_security.repository;

import com.it_academyproject.jwt_security.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>
{
	List<PasswordResetToken> findByMyAppUserId(String id);

	List<PasswordResetToken> findByToken(String id);
}
