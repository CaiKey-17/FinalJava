//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity @Getter @Setter @NoArgsConstructor
//public class ImageLand {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String imageLink;
//
//    @ManyToOne
//    @JoinColumn(name = "landForSale_id")
//    private LandForSale landForSale;
//
//    @ManyToOne
//    @JoinColumn(name = "landForRent_id")
//    private LandForRent landForRent;
//
//    public ImageLand(String imageLink) {
//        this.imageLink = imageLink;
//    }
//}
