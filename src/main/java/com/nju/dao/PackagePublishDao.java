package com.nju.dao;

import com.nju.model.PackagePublish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackagePublishDao extends JpaRepository<PackagePublish, Long>, BaseRepository<PackagePublish, Long>  {

    List<PackagePublish> findByResIdAndDate(Long resId, String date);

    List<PackagePublish> findByNameAndResIdAndDate(String name, Long resId, String date);
}
