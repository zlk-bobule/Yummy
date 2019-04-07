package com.nju.dao;

import com.nju.model.FinanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceLogDao extends JpaRepository<FinanceLog, Long>, BaseRepository<FinanceLog, Long>  {
    List<FinanceLog> findByDate(String date);
}
