package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.ImageLand;
import com.example.chuyentrang.model.ImageNews;
import com.example.chuyentrang.model.LandForSale;
import com.example.chuyentrang.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ImageNewsRepository extends JpaRepository<ImageNews, Integer> {
    List<ImageNews> findByNews(News news);

}
