package com.lgh.FlipMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	@NativeQuery("SELECT product_num FROM like_product WHERE user_num = ?1")
	List<String> findByUserNum(Long userNum);

	@NativeQuery("SELECT COUNT(*) FROM like_product WHERE user_num = ?1 AND product_num = ?2")
	int countByUserNumAndProductNum(Long userNum, Long productNum);

	@NativeQuery("SELECT num FROM like_product WHERE user_num = ?1 AND product_num = ?2")
	Long findByUserNumAndProductNum(Long UserNum, Long productNum);

}
