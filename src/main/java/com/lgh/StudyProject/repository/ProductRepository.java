package com.lgh.StudyProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@NativeQuery(value = "SELECT num, product_name, price, category, stock, description, image_path FROM product WHERE user_num <> ?1")
	List<Object[]> findByUserNumNot(Long userNum);
	
}
