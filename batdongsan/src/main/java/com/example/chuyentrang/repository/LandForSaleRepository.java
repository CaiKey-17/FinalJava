package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.LandForSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LandForSaleRepository extends JpaRepository<LandForSale, Integer> {
    List<LandForSale> findByBrokerId(Long brokerId);
    List<LandForSale> findByTypeIn(List<String> types);

    Page<LandForSale> findByTypeIn(List<String> types, Pageable pageable);

    List<LandForSale> findByBrokerIdAndTypeIn(Long userId, List<String> types);

    Page<LandForSale> findByTypeInAndProvince(List<String> types, String province, Pageable pageable);

    @Query("SELECT l FROM LandForSale l WHERE " +
            "(COALESCE(:propertyTypes, null) IS NULL OR l.propertyType IN :propertyTypes) " +
            "AND l.price BETWEEN :minPrice AND :maxPrice " +
            "AND l.type IN :landTypes " +
            "AND l.area BETWEEN :minArea AND :maxArea " +
            "AND (:numberOfBedRooms = 0 OR " +
            "(:numberOfBedRooms = 5 AND l.numberOfBedRooms >= :numberOfBedRooms) OR " +
            "(l.numberOfBedRooms = :numberOfBedRooms))")
    Page<LandForSale> findByPropertyTypesAndPriceAndTypesAndAreaAndNumberOfBedRooms(
            List<String> propertyTypes,
            int minPrice,
            int maxPrice,
            List<String> landTypes,
            int minArea,
            int maxArea,
            int numberOfBedRooms,
            Pageable pageable);


}
