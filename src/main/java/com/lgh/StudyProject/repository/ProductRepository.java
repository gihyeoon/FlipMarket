package com.lgh.StudyProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.StudyProject.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@NativeQuery(value = "SELECT COUNT(*) FROM product WHERE product_name = ?1 and category = ?2 and user_num = ?3")
	int countByProductNameAndCategory(String productName, String category, Long userNum);

	@NativeQuery(value = "SELECT num, product_name, price, category, stock, description, image_path FROM product WHERE user_num <> ?1 AND stock > 0")
	List<Object[]> findByUserNumNot(Long userNum);

	@NativeQuery(value = "select num, product_name, price, category, stock, description, image_path FROM product where user_num <> ?2 and stock > 0 and product_name like ?1")
	List<Object[]> findByKeywordAndUserNum(String keyword, Long userNum);

	@NativeQuery(value = "update product set stock = ?2 where num = ?1")
	@Modifying
	int updateProductStock(Long productNum, int stock);
	
	@NativeQuery(value = "SELECT num, product_name, price, category, stock, description, image_path FROM product WHERE user_num = ?1 ORDER BY updated_at LIMIT 3")
	List<Object[]> findByUserNum(Long userNum);
	
}
