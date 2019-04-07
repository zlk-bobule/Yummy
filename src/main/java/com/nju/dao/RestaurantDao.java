package com.nju.dao;

import com.nju.enums.ResState;
import com.nju.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantDao extends JpaRepository<Restaurant, Long>, BaseRepository<Restaurant, Long> {
    List<Restaurant> findByType(String type);

    List<Restaurant> findByIdentifyCode(String identifyCode);

    List<Restaurant> findByResState(ResState resState);

    List<Restaurant> findByIdentifyCodeAndResState(String identifyCode, ResState resState);
}
