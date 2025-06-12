package com.lgh.FlipMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lgh.FlipMarket.entity.ViewCount;

@Repository
public interface ViewCountRepository extends JpaRepository<ViewCount, Long> {

}
