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
//public class Package {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String name;
//    private double price;
//    private int quantity;
//    private LocalDateTime expiry;
//
//    @OneToMany(mappedBy = "available_id", cascade = CascadeType.ALL)
//    private List<Available> availables;
//
//    public Package(String name, double price, int quantity, LocalDateTime expiry) {
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.expiry = expiry;
//    }
//
//    public void addAvailable(Available available) {
//        availables.add(available);
//        available.setPackagee(this);
//    }
//
//    public void removeAvailable(Available available) {
//        availables.remove(available);
//        available.setPackagee(null);
//    }
//}
