package com.lgh.FlipMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Modifying
	@NativeQuery("UPDATE address SET is_default = 0 WHERE user_num = ?1")
	int updateDefaultAddress(Long userNum);
	
	@Modifying
	@NativeQuery("UPDATE address SET is_default = 1 WHERE num = ?1")
	int setDefaultAddress(Long addressNum);

	@NativeQuery(value = "SELECT num, zonecode, road_address, jibun_address, detail_address, building_name, address_type, is_default FROM address where user_num = ?1")
	List<Object[]> findByUserNum(Long userNum);
	
}
