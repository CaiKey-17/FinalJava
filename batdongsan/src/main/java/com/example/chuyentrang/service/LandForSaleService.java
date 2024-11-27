//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.LandForSale;
//import com.example.chuyentrang.repository.LandForSaleRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LandForSaleService {
//    private LandForSaleRepository landForSaleRepository;
//
//    // Create
//    public LandForSale createLandForSale(LandForSale landForSale) {
//        return landForSaleRepository.save(landForSale);
//    }
//
//    // Read
//    public Optional<LandForSale> getLandForSaleById(int id) {
//        return landForSaleRepository.findById(id);
//    }
//
//    public Iterable<LandForSale> getAllLandForSales() {
//        return landForSaleRepository.findAll();
//    }
//
//    // Update
//    public LandForSale updateLandForSale(int id, LandForSale updatedLandForSale) {
//        return landForSaleRepository.findById(id)
//                .map(existingLandForSale -> {
//                    // Update fields here
//                    return landForSaleRepository.save(existingLandForSale);
//                }).orElseThrow(() -> new RuntimeException("LandForSale not found"));
//    }
//
//    // Delete
//    public void deleteLandForSale(int id) {
//        landForSaleRepository.deleteById(id);
//    }
//}
