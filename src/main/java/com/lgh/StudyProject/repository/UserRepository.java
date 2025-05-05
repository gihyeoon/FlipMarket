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
	
	@NativeQuery(value = "SELECT pwd FROM user WHERE num = ?1")
	String selectUserPwdByNum(Long num);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.id = ?1 WHERE u.num = ?2")
	int updateId(String newId, Long num);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.phone_num = ?1 WHERE u.num = ?2")
	int updatePhoneNum(String newPhoneNum, Long num);
	
	@Modifying
	@NativeQuery(value = "UPDATE user u SET u.pwd = ?1 WHERE u.num = ?2")
	int updatePwd(String newPwd, Long num);
	
}
