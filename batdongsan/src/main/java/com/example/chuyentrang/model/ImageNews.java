//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity @Getter @Setter @NoArgsConstructor
//public class ImageNews {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String imageLink;
//
//    @ManyToOne
//    @JoinColumn(name = "news_id")
//    private News news;
//
//    public ImageNews(String imageLink) {
//        this.imageLink = imageLink;
//    }
//}
