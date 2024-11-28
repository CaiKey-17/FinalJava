package com.example.chuyentrang.service;

import com.example.chuyentrang.repository.PackageRepository;
import com.example.chuyentrang.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;

    // Create
    public Package createPackage(Package packagee) {
        return packageRepository.save(packagee);
    }

    // Read
    public Optional<Package> getPackageById(int id) {
        return packageRepository.findById(id);
    }

    public Iterable<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    // Update
    public Package updatePackage(int id, Package updatedPackage) {
        return packageRepository.findById(id)
                .map(existingPackage -> {
                    // Update fields here
                    return packageRepository.save(existingPackage);
                }).orElseThrow(() -> new RuntimeException("Package not found"));
    }

    // Delete
    public void deletePackage(int id) {
        packageRepository.deleteById(id);
    }


    public List<Package> getAllPackage(){
        return packageRepository.findAll();
    }
}

