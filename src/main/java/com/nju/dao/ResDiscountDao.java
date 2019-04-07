package com.nju.dao;

import com.nju.model.ResDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResDiscountDao extends JpaRepository<ResDiscount, Long>, BaseRepository<ResDiscount, Long> {
    List<ResDiscount> findByResIdAndDate(Long resId, String date);
}