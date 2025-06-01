package com.lgh.FlipMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
