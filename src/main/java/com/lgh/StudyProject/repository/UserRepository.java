package com.lgh.StudyProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	@NativeQuery(value = "SELECT COUNT(*) FROM user WHERE email = ?1")
	int countUserByEmail(String email);
	
	@NativeQuery(value = "SELECT pwd FROM user WHERE num = ?1")
	String selectUserPwdByNum(Long num);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.email = ?1 WHERE u.num = ?2")
	int updateEmail(String newEmail, Long num);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.phone_num = ?1 WHERE u.num = ?2")
	int updatePhoneNum(String newPhoneNum, Long num);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.pwd = ?1 WHERE u.num = ?2")
	int updatePwd(String newPwd, Long num);
	
}
