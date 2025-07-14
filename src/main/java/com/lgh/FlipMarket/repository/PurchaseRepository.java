package com.lgh.FlipMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	@NativeQuery("SELECT num, quantity, total_price, user_num, product_num FROM purchase WHERE user_num = ?1")
	List<Object[]> findByUserNum(Long userNum);
	
}
