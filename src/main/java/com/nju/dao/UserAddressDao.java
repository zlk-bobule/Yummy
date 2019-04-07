package com.nju.dao;

import com.nju.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAddressDao extends JpaRepository<UserAddress, Long>, BaseRepository<UserAddress, Long> {
    List<UserAddress> findByUserId(Long userId);
}
