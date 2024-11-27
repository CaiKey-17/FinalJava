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
//public class Project {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String description;
//    private String legal;
//    private double price;
//    private int numberOfBuildings;
//    private String nameOfProject;
//    private String province;
//    private String district;
//    private String ward;
//    private double area;
//    private int numberOfAparments;
//
//    @OneToMany(mappedBy = "imageProject_id", cascade =  CascadeType.ALL)
//    private List<ImageProject> imageProjects;
//
//    @OneToMany(mappedBy = "landForSale_id", cascade = CascadeType.ALL)
//    private List<LandForSale> landForSales;
//
//    @OneToMany(mappedBy = "landForRent_id", cascade = CascadeType.ALL)
//    private List<LandForRent> landForRents;
//
//    @ManyToOne
//    @JoinColumn(name = "broker_id")
//    private Broker broker;
//
//    @ManyToOne
//    @JoinColumn(name = "available_id")
//    private Available available;
//
//    public Project(String description, String legal, double price, int numberOfBuildings, String nameOfProject,
//                   String province, String district, String ward, double area, int numberOfAparments) {
//        this.description = description;
//        this.legal = legal;
//        this.price = price;
//        this.numberOfBuildings = numberOfBuildings;
//        this.nameOfProject = nameOfProject;
//        this.province = province;
//        this.district = district;
//        this.ward = ward;
//        this.area = area;
//        this.numberOfAparments = numberOfAparments;
//    }
//
//    public void addImage(ImageProject imageProject) {
//        imageProjects.add(imageProject);
//        imageProject.setProject(this);
//    }
//
//    public void removeImage(ImageProject imageProject) {
//        imageProjects.remove(imageProject);
//        imageProject.setProject(null);
//    }
//
//    public void addLandForSale(LandForSale landForSale) {
//        landForSales.add(landForSale);
//        landForSale.setProject(this);
//    }
//
//    public void removeLandForSale(LandForSale landForSale) {
//        landForSales.remove(landForSale);
//        landForSale.setProject(null);
//    }
//
//    public void addLandForRent(LandForRent landForRent) {
//        landForRents.add(landForRent);
//        landForRent.setProject(this);
//    }
//
//    public void removeLandForRent(LandForRent landForRent) {
//        landForRents.remove(landForRent);
//        landForRent.setProject(null);
//    }
//}
