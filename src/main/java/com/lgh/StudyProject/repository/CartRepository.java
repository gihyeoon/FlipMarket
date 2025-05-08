package com.lgh.StudyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
