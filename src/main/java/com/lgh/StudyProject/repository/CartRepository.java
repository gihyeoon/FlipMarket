package com.lgh.StudyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@NativeQuery(value = "SELECT COUNT(*) FROM cart WHERE user_num = ?1 and productNum = ?2")
	int countCartByUserNumAndProductNum(Long userNum, Long productNum);

}
