package com.example.chuyentrang.service;

import com.example.chuyentrang.model.*;
import com.example.chuyentrang.repository.AvailableRepository;
import com.example.chuyentrang.repository.ImageLandRepository;
import com.example.chuyentrang.repository.LandForSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private ImageLandRepository imageLandRepository;


    public void postLandForSale(LandForSale landForSale, Long userId, int availableId, List<String> imageLinks) {
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
        User user = userService.findById(userId);
        landForSale.setAvailable(available);
        landForSale.setBroker(user);
        LandForSale savedLand = landForSaleRepository.save(landForSale);

        // Lưu danh sách ảnh
        if (imageLinks != null && !imageLinks.isEmpty()) {
            List<ImageLand> imageLands = imageLinks.stream()
                    .map(link -> new ImageLand(link, savedLand))
                    .collect(Collectors.toList());
            imageLandRepository.saveAll(imageLands);
        }
    }

    // Xử lý ảnh nếu có
//        if (images != null && images.length > 0) {
//            for (MultipartFile image : images) {
//                String fileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
//                File dest = new File("uploads/" + fileName);
//                try {
//                    image.transferTo(dest); // Tải ảnh lên thư mục server
//
//                    // Lưu thông tin ảnh vào database
//                    ImageLand imageLand = new ImageLand(fileName);
//                    imageLand.setLandForSale(landForSale); // Gắn LandForSale vào ImageLand
//                    imageLandRepository.save(imageLand);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}
