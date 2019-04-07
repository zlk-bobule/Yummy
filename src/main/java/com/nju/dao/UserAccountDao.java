package com.nju.dao;

import com.nju.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountDao extends JpaRepository<UserAccount, Long>, BaseRepository<UserAccount, Long> {
    List<UserAccount> findByUserId(Long userId);
}
