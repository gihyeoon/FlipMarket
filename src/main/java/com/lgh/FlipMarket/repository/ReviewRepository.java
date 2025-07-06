package com.lgh.FlipMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@NativeQuery(value = "SELECT num, user_num, product_num, rating, created_at FROM review WHERE product_num = ?1")
	List<Object[]> findByProductNum(Long productNum);
	
}
