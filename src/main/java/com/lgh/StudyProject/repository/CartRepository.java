package com.lgh.StudyProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@NativeQuery(value = "SELECT COUNT(*) FROM cart WHERE user_num = ?1 and product_num = ?2")
	int countCartByUserNumAndProductNum(Long userNum, Long productNum);

	@NativeQuery(value = "select c.num, p.product_name, p.category, p.description, p.image_path, p.price, c.quantity, p.stock from cart c, product p, user u where c.product_num = p.num and c.user_num = u.num and c.user_num = ?1")
	List<Object[]> findByUserNum(Long userNum);
	
	@Modifying
	@NativeQuery(value = "delete from cart where num = ?1")
	int deleteByNum(Long num);
	
}
