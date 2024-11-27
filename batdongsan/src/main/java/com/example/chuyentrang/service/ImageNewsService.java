//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.ImageNews;
//import com.example.chuyentrang.repository.ImageNewsRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ImageNewsService {
//    private ImageNewsRepository imageNewsRepository;
//
//    // Create
//    public ImageNews createImageNews(ImageNews imageNews) {
//        return imageNewsRepository.save(imageNews);
//    }
//
//    // Read
//    public Optional<ImageNews> getImageNewsById(int id) {
//        return imageNewsRepository.findById(id);
//    }
//
//    public Iterable<ImageNews> getAllImageNewss() {
//        return imageNewsRepository.findAll();
//    }
//
//    // Update
//    public ImageNews updateImageNews(int id, ImageNews updatedImageNews) {
//        return imageNewsRepository.findById(id)
//                .map(existingImageNews -> {
//                    // Update fields here
//                    return imageNewsRepository.save(existingImageNews);
//                }).orElseThrow(() -> new RuntimeException("ImageNews not found"));
//    }
//
//    // Delete
//    public void deleteImageNews(int id) {
//        imageNewsRepository.deleteById(id);
//    }
//}
