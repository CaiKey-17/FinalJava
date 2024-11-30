package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.ImageLand;
import com.example.chuyentrang.model.LandForSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
public interface ImageLandRepository extends JpaRepository<ImageLand, Integer> {
    List<ImageLand> findByLandForSale(LandForSale landForSale);
}
