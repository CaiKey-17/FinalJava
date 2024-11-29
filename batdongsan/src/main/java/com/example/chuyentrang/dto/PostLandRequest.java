package com.example.chuyentrang.dto;

import com.example.chuyentrang.model.LandForSale;
import org.springframework.web.multipart.MultipartFile;

public class PostLandRequest {
    private LandForSale landForSale;
    private Long userId;
    private int availableId;


    // Getters and Setters
    public LandForSale getLandForSale() {
        return landForSale;
    }

    public void setLandForSale(LandForSale landForSale) {
        this.landForSale = landForSale;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getAvailableId() {
        return availableId;
    }

    public void setAvailableId(int availableId) {
        this.availableId = availableId;
    }
}
