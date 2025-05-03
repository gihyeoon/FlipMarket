package com.lgh.StudyProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(String id);
	
	boolean existsById(String id);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.id = ?1 WHERE u.num = ?2")
	int updateId(String newId, Long num);
	
}
