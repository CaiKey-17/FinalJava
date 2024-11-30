package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.LandForSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LandForSaleRepository extends JpaRepository<LandForSale, Integer> {
    List<LandForSale> findByBrokerId(Long brokerId);
    List<LandForSale> findByTypeIn(List<String> types);

    List<LandForSale> findByBrokerIdAndTypeIn(Long userId, List<String> types);
}
