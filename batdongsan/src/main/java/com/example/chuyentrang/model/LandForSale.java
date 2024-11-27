//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity @Getter @Setter @NoArgsConstructor
//public class LandForSale {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String name;
//    private double price;
//    private double area;
//    private String province;
//    private String district;
//    private String ward;
//    private String interior;
//    private int numberOfToilets;
//    private int numberOfBedRooms;
//    private String description;
//    private LocalDateTime datePosted;
//    private String type;
//    private String legal;
//
//    @OneToMany(mappedBy = "imageLand_id", cascade = CascadeType.ALL)
//    private List<ImageLand> imageLands;
//
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project project;
//
//    @ManyToOne
//    @JoinColumn(name = "broker_id")
//    private Broker broker;
//
//    @ManyToOne
//    @JoinColumn(name = "available_id")
//    private Available available;
//
//    public LandForSale(String name, double price, double area, String province, String district, String ward,
//                       String interior, int numberOfToilets, int numberOfBedRooms, String description, LocalDateTime datePoste,
//                       String type, String legal) {
//        this.name = name;
//        this.price = price;
//        this.area = area;
//        this.province = province;
//        this.district = district;
//        this.ward = ward;
//        this.interior = interior;
//        this.numberOfToilets = numberOfToilets;
//        this.numberOfBedRooms = numberOfBedRooms;
//        this.description = description;
//        this.datePosted = datePoste;
//        this.type = type;
//        this.legal = legal;
//    }
//
//    public void addImage(ImageLand imageLand) {
//        imageLands.add(imageLand);
//        imageLand.setLandForSale(this);
//    }
//
//    public void removeImage(ImageLand imageLand) {
//        imageLands.remove(imageLand);
//        imageLand.setLandForSale(null);
//    }
//}
