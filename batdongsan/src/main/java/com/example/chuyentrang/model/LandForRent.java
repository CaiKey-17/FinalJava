//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity @Getter @Setter @NoArgsConstructor
//public class LandForRent {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String postName;
//    private double price;
//    private double area;
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
//    public LandForRent(String postName, double price, double area) {
//        this.postName = postName;
//        this.price = price;
//        this.area = area;
//    }
//
//    public void addImage(ImageLand imageLand) {
//        imageLands.add(imageLand);
//        imageLand.setLandForRent(this);
//    }
//
//    public void removeImage(ImageLand imageLand) {
//        imageLands.remove(imageLand);
//        imageLand.setLandForRent(null);
//    }
//}
