package com.example.chuyentrang.service;

import com.example.chuyentrang.model.Available;
import com.example.chuyentrang.model.Deposit;
import com.example.chuyentrang.model.LandForSale;
import com.example.chuyentrang.model.User;
import com.example.chuyentrang.repository.AvailableRepository;
import com.example.chuyentrang.repository.LandForSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LandForSaleService {

    @Autowired
    private LandForSaleRepository landForSaleRepository;

    // Create
    public LandForSale createLandForSale(LandForSale landForSale) {
        return landForSaleRepository.save(landForSale);
    }


    public List<LandForSale> listLandByBroker(Long userId) {
        return landForSaleRepository.findByBrokerId(userId);
    }


    // Read
    public Optional<LandForSale> getLandForSaleById(int id) {
        return landForSaleRepository.findById(id);
    }

    public Iterable<LandForSale> getAllLandForSales() {
        return landForSaleRepository.findAll();
    }

    // Update
    public LandForSale updateLandForSale(int id, LandForSale updatedLandForSale) {
        return landForSaleRepository.findById(id)
                .map(existingLandForSale -> {
                    // Update fields here
                    return landForSaleRepository.save(existingLandForSale);
                }).orElseThrow(() -> new RuntimeException("LandForSale not found"));
    }

    // Delete
    public void deleteLandForSale(int id) {
        landForSaleRepository.deleteById(id);
    }

    @Autowired
    private AvailableService availableService;

    @Autowired
    private UserService userService;

    @Autowired
    private AvailableRepository availableRepository;

    public void postLandForSale(LandForSale landForSale, Long userId, int availableId) {
        // Tìm Available dựa trên availableId và userId
        Available available = availableRepository.findByOrderIdAndBroker_Id(availableId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Available not found or does not belong to user"));

        // Kiểm tra số lượng còn khả dụng
        if (available.getQuantityAvailable() <= 0) {
            throw new IllegalArgumentException("No available quantity left for posting.");
        }

        // Trừ quantityAvailable
        available.setQuantityAvailable(available.getQuantityAvailable() - 1);
        availableRepository.save(available);

        // Lưu LandForSale
        User u = userService.findById(userId);
        Available a = availableService.findById(availableId);

        landForSale.setAvailable(a);
        landForSale.setBroker(u);
        LandForSale l = landForSale;
        landForSaleRepository.save(l);
    }
}
