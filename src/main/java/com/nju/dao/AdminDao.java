package com.nju.dao;

import com.nju.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao  extends JpaRepository<Admin, Long>, BaseRepository<Admin, Long>  {
}
