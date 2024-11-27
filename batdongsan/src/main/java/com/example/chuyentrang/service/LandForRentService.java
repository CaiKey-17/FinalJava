//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.LandForRent;
//import com.example.chuyentrang.repository.LandForRentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LandForRentService {
//    private LandForRentRepository landForRentRepository;
//
//    @Autowired
//    public LandForRentService(LandForRentRepository landForRentRepository) {
//        this.landForRentRepository = landForRentRepository;
//    }
//
//    // Create
//    public LandForRent createLandForRent(LandForRent landForRent) {
//        return landForRentRepository.save(landForRent);
//    }
//
//    // Read
//    public Optional<LandForRent> getLandForRentById(int id) {
//        return landForRentRepository.findById(id);
//    }
//
//    public Iterable<LandForRent> getAllLandForRents() {
//        return landForRentRepository.findAll();
//    }
//
//    // Update
//    public LandForRent updateLandForRent(int id, LandForRent updatedLandForRent) {
//        return landForRentRepository.findById(id)
//                .map(existingLandForRent -> {
//                    // Update fields here
//                    return landForRentRepository.save(existingLandForRent);
//                }).orElseThrow(() -> new RuntimeException("LandForRent not found"));
//    }
//
//    // Delete
//    public void deleteLandForRent(int id) {
//        landForRentRepository.deleteById(id);
//    }
//}
