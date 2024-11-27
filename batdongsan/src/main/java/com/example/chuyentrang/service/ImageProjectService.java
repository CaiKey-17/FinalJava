//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.ImageProject;
//import com.example.chuyentrang.repository.ImageProjectRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class ImageProjectService {
//    private ImageProjectRepository imageProjectRepository;
//
//    // Create
//    public ImageProject createImageProject(ImageProject imageProject) {
//        return imageProjectRepository.save(imageProject);
//    }
//
//    // Read
//    public Optional<ImageProject> getImageProjectById(int id) {
//        return imageProjectRepository.findById(id);
//    }
//
//    public Iterable<ImageProject> getAllImageProjects() {
//        return imageProjectRepository.findAll();
//    }
//
//    // Update
//    public ImageProject updateImageProject(int id, ImageProject updatedImageProject) {
//        return imageProjectRepository.findById(id)
//                .map(existingImageProject -> {
//                    // Update fields here
//                    return imageProjectRepository.save(existingImageProject);
//                }).orElseThrow(() -> new RuntimeException("ImageProject not found"));
//    }
//
//    // Delete
//    public void deleteImageProject(int id) {
//        imageProjectRepository.deleteById(id);
//    }
//}
//
