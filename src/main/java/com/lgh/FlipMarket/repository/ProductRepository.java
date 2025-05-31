package com.lgh.FlipMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@NativeQuery(value = "SELECT COUNT(*) FROM product WHERE product_name = ?1 and category = ?2 and user_num = ?3")
	int countByProductNameAndCategory(String productName, String category, Long userNum);

	@NativeQuery(value = "SELECT num, product_name, price, category, stock, description, image_path FROM product WHERE user_num <> ?1 AND stock > 0")
	List<Object[]> findByUserNumNot(Long userNum);

	@NativeQuery(value = "SELECT num, product_name, price, category, stock, description, image_path FROM product WHERE user_num <> ?2 AND stock > 0 AND product_name like ?1")
	List<Object[]> findByKeywordAndUserNum(String keyword, Long userNum);

	@NativeQuery(value = "UPDATE product SET stock = ?2 WHERE num = ?1")
	@Modifying
	int updateProductStock(Long productNum, int stock);

	@NativeQuery(value = "SELECT num, product_name, price, category, stock, description, image_path FROM product WHERE user_num = ?1 ORDER BY updated_at LIMIT 3")
	List<Object[]> findByUserNum(Long userNum);

	@NativeQuery(value = "UPDATE product SET image_path = ?1, product_name = ?2, category = ?3, stock = ?4, price = ?5, description = ?6 WHERE num = ?7")
	@Modifying
	int updateProduct(String imagePath, String productName, String category, int stock, int price, String description, Long productNum);

}
