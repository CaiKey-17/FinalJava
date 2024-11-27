//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity @Getter @Setter @NoArgsConstructor
//public class Deposit {
//    @Id
//    @GeneratedValue
//    private int id;
//    private double price;
//    private LocalDateTime dateTime;
//    private String status;
//
//    @ManyToOne
//    @JoinColumn(name = "broker_id")
//    private Broker broker;
//
//    public Deposit(double price, LocalDateTime dateTime, String status) {
//        this.price = price;
//        this.dateTime = dateTime;
//        this.status = status;
//    }
//}
