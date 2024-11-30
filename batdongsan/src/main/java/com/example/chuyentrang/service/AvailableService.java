package com.example.chuyentrang.service;

import com.example.chuyentrang.model.Available;
import com.example.chuyentrang.repository.AvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
@Service
public class AvailableService {
    @Autowired
    private AvailableRepository availableRepository;

    public Available save(Available available) {
        return availableRepository.save(available);
    }

    public Iterable<Available> findAll() {
        return availableRepository.findAll();
    }

    public void delete(Available available) {
        availableRepository.delete(available);
    }

    public boolean canPost(Long brokerId) {
        Optional<Available> available = availableRepository.findByBroker_IdAndExpirationDateAfterAndQuantityAvailableGreaterThan(
                brokerId,
                LocalDateTime.now(),
                0
        );

        return available.isPresent();
    }

    public Available findById(int id) {
        return availableRepository.findById(id).get();
    }

    @Scheduled(cron = "0 0 0 * * *") // Lên lịch mỗi ngày vào lúc nửa đêm
    public void deleteExpiredAvailable() {
        LocalDateTime now = LocalDateTime.now();

        // Lấy danh sách tất cả các bản ghi 'Available' có expirationDate nhỏ hơn ngày hiện tại
        List<Available> expiredAvailableList = availableRepository.findByExpirationDateBefore(now);

        // Xoá các bản ghi này khỏi cơ sở dữ liệu
        availableRepository.deleteAll(expiredAvailableList);
        System.out.println("Đã xoá các bản ghi 'Available' hết hạn.");
    }
}
