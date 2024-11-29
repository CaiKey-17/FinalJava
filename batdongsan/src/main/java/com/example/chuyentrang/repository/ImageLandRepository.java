package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.ImageLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ImageLandRepository extends JpaRepository<ImageLand, Integer> {
}
