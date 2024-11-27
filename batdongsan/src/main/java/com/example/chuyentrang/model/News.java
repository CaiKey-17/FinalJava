//package com.example.chuyentrang.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Getter @Setter @NoArgsConstructor
//public class News {
//    @Id
//    @GeneratedValue
//    private int id;
//    private String title;
//    private String summaryOfContent;
//    private String content;
//
//    @OneToMany(mappedBy = "imageNews_id", cascade = CascadeType.ALL)
//    private List<ImageNews> imageNewsList;
//
//    @ManyToOne
//    @JoinColumn(name = "broker_id")
//    private Broker broker;
//
//    @ManyToOne
//    @JoinColumn(name = "available_id")
//    private Available available;
//
//    public News(String title, String summaryOfContent, String content) {
//        this.title = title;
//        this.summaryOfContent = summaryOfContent;
//        this.content = content;
//    }
//
//    public void addImage(ImageNews imageNews) {
//        imageNewsList.add(imageNews);
//        imageNews.setNews(this);
//    }
//
//    public void removeImage(ImageNews imageNews) {
//        imageNewsList.remove(imageNews);
//        imageNews.setNews(null);
//    }
//}
