//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity @Getter @Setter @NoArgsConstructor
//public class ImageProject {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String imageLink;
//
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project project;
//
//    public ImageProject(String imageLink) {
//        this.imageLink = imageLink;
//    }
//}
