package com.example.chuyentrang.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor
public class Available {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String statusPayment;
    private LocalDateTime purchaseDate;
    private int quantityAvailable;
    private int total;
    private LocalDateTime expirationDate;
//
//    @OneToMany(mappedBy = "news_id", cascade = CascadeType.ALL)
//    private List<News> newsList;
//
//    @OneToMany(mappedBy = "landForSale_id", cascade = CascadeType.ALL)
//    private List<LandForSale> landForSaleList;
//
//    @OneToMany(mappedBy = "project_id", cascade = CascadeType.ALL)
//    private List<Project> projectList;
//
//    @OneToMany(mappedBy = "landForRent", cascade = CascadeType.ALL)
//    private List<LandForRent> landForRentList;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User broker;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package packagee;

    public Available(String statusPayment, LocalDateTime purchaseDate, int quantityAvailable,
                     int total, LocalDateTime expirationDate) {
        this.statusPayment = statusPayment;
        this.purchaseDate = purchaseDate;
        this.quantityAvailable = quantityAvailable;
        this.total = total;
        this.expirationDate = expirationDate;
    }

//    public void addNews(News news) {
//        newsList.add(news);
//        news.setAvailable(this);
//    }
//
//    public void removeNews(News news) {
//        newsList.remove(news);
//        news.setAvailable(null);
//    }
//
//    public void addLandForSale(LandForSale landForSale) {
//        landForSaleList.add(landForSale);
//        landForSale.setAvailable(this);
//    }
//
//    public void removeLandForSale(LandForSale landForSale) {
//        landForSaleList.remove(landForSale);
//        landForSale.setAvailable(null);
//    }
//
//    public void addProject(Project project) {
//        projectList.add(project);
//        project.setAvailable(this);
//    }
//
//    public void removeProject(Project project) {
//        projectList.remove(project);
//        project.setAvailable(null);
//    }
//
//    public void addLandForRent(LandForRent landForRent) {
//        landForRentList.add(landForRent);
//        landForRent.setAvailable(this);
//    }
//
//    public void removeLandForRent(LandForRent landForRent) {
//        landForRentList.remove(landForRent);
//        landForRent.setAvailable(null);
//    }
}
