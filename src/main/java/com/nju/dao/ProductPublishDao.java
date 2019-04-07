package com.nju.dao;

import com.nju.model.ProductPublish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPublishDao extends JpaRepository<ProductPublish, Long>, BaseRepository<ProductPublish, Long> {

    List<ProductPublish> findByResIdAndDate(Long resId, String Date);

    List<ProductPublish> findByNameAndResIdAndDate(String name, Long resId, String date);

}
