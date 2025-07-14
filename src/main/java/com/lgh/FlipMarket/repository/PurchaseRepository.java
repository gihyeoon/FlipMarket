package com.lgh.FlipMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	@NativeQuery("SELECT num, quantity, total_price, user_num, product_num, created_at, (select case when count(*) > 0 then true else false end as hasReview from review r where r.user_num = p.user_num and r.product_num = p.product_num) as has_review FROM purchase p where p.user_num = ?1")
	List<Object[]> findByUserNum(Long userNum);
	
}
