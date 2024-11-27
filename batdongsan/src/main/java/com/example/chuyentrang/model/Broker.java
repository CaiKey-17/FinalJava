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
//public class Broker {
//    @Id
//    @GeneratedValue
//    private String account;
//    private String password;
//    private String fullName;
//    private String email;
//    private String address;
//    private String zoneWorking;
//    private double balance;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    @OneToMany(mappedBy = "news_id", cascade = CascadeType.ALL)
//    private List<News> news;
//
//    @OneToMany(mappedBy = "landForSale_id", cascade = CascadeType.ALL)
//    private List<LandForSale> landForSale;
//
//    @OneToMany(mappedBy = "project_id", cascade = CascadeType.ALL)
//    private List<Project> projects;
//
//    @OneToMany(mappedBy = "landForRent_id", cascade = CascadeType.ALL)
//    private List<LandForRent> landForRent;
//
//    @OneToMany(mappedBy = "available_id", cascade = CascadeType.ALL)
//    private List<Available> available;
//
//    @OneToMany(mappedBy = "deposit_id", cascade = CascadeType.ALL)
//    private List<Deposit> deposit;
//
//    public Broker(String account, String password, String fullName, String email,
//                  String address, String zoneWorking, double balance) {
//        this.account = account;
//        this.password = password;
//        this.fullName = fullName;
//        this.email = email;
//        this.address = address;
//        this.zoneWorking = zoneWorking;
//        this.balance = balance;
//    }
//
//    public void addNews(News news) {
//        this.news.add(news);
//        news.setBroker(this);
//    }
//
//    public void removeNews(News news) {
//        this.news.remove(news);
//        news.setBroker(null);
//    }
//
//    public void addLandForSale(LandForSale landForSale) {
//        this.landForSale.add(landForSale);
//        landForSale.setBroker(this);
//    }
//
//    public void removeLandForSale(LandForSale landForSale) {
//        this.landForSale.remove(landForSale);
//        landForSale.setBroker(null);
//    }
//
//    public void addProject(Project project) {
//        this.projects.add(project);
//        project.setBroker(this);
//    }
//
//    public void removeProject(Project project) {
//        this.projects.remove(project);
//        project.setBroker(null);
//    }
//
//    public void addLandForRent(LandForRent landForRent) {
//        this.landForRent.add(landForRent);
//        landForRent.setBroker(this);
//    }
//
//    public void removeLandForRent(LandForRent landForRent) {
//        this.landForRent.remove(landForRent);
//        landForRent.setBroker(null);
//    }
//
//    public void addAvailable(Available available) {
//        this.available.add(available);
//        available.setBroker(this);
//    }
//
//    public void removeAvailable(Available available) {
//        this.available.remove(available);
//        available.setBroker(null);
//    }
//
//    public void addDeposit(Deposit deposit) {
//        this.deposit.add(deposit);
//        deposit.setBroker(this);
//    }
//
//    public void removeDeposit(Deposit deposit) {
//        this.deposit.remove(deposit);
//        deposit.setBroker(null);
//    }
//}
