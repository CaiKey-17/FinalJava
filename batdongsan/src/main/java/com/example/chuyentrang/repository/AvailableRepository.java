package com.example.chuyentrang.repository;

import com.example.chuyentrang.model.Available;
import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AvailableRepository extends JpaRepository<Available, Integer> {
    List<Available> findByBroker(User broker);
    List<Available> findByBrokerAndQuantityAvailableGreaterThan(User broker, int quantityAvailable);
    Optional<Available> findByBroker_IdAndExpirationDateAfterAndQuantityAvailableGreaterThan(
            Long brokerId, LocalDateTime expirationDate, int quantityAvailable);
    Optional<Available> findByOrderIdAndBroker_Id(int orderId, Long brokerId);

    List<Available> findByExpirationDateBefore(LocalDateTime now);

    @Query("SELECT a.packagee.name, COUNT(a.orderId) AS purchaseCount, SUM(a.total) AS totalRevenue " +
            "FROM Available a " +
            "GROUP BY a.packagee.name")
    List<Object[]> getPackageStatistics();

    List<Available> findByBrokerAndQuantityAvailableGreaterThanAndExpirationDateAfter(User broker, int quantity, LocalDateTime expirationDate);


}
