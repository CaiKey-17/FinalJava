//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.ImageLand;
//import com.example.chuyentrang.repository.ImageLandRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ImageLandService {
//    private  ImageLandRepository imageLandRepository;
//
//
//    // Create
//    public ImageLand createImageLand(ImageLand imageLand) {
//        return imageLandRepository.save(imageLand);
//    }
//
//    // Read
//    public Optional<ImageLand> getImageLandById(int id) {
//        return imageLandRepository.findById(id);
//    }
//
//    public Iterable<ImageLand> getAllImageLands() {
//        return imageLandRepository.findAll();
//    }
//
//    // Update
//    public ImageLand updateImageLand(int id, ImageLand updatedImageLand) {
//        return imageLandRepository.findById(id)
//                .map(existingImageLand -> {
//                    // Update fields here
//                    return imageLandRepository.save(existingImageLand);
//                }).orElseThrow(() -> new RuntimeException("ImageLand not found"));
//    }
//
//    // Delete
//    public void deleteImageLand(int id) {
//        imageLandRepository.deleteById(id);
//    }
//}
//
//
