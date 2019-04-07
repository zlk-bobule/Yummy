package com.nju.dao;

import com.nju.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long>, BaseRepository<User, Long> {
    List<User> findByMail(String mail);

    List<User> findByDate(String date);
}
