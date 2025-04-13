package com.lgh.StudyProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgh.StudyProject.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(String id);
	
}
