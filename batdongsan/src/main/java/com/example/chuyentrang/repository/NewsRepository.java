package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByBrokerId(Long userId);
}
