//package com.example.chuyentrang.service;
//
//import com.example.chuyentrang.model.News;
//import com.example.chuyentrang.repository.NewsRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class NewsService {
//    private NewsRepository newsRepository;
//
//
//    // Create
//    public News createNews(News news) {
//        return newsRepository.save(news);
//    }
//
//    // Read
//    public Optional<News> getNewsById(int id) {
//        return newsRepository.findById(id);
//    }
//
//    public Iterable<News> getAllNewss() {
//        return newsRepository.findAll();
//    }
//
//    // Update
//    public News updateNews(int id, News updatedNews) {
//        return newsRepository.findById(id)
//                .map(existingNews -> {
//                    // Update fields here
//                    return newsRepository.save(existingNews);
//                }).orElseThrow(() -> new RuntimeException("News not found"));
//    }
//
//    // Delete
//    public void deleteNews(int id) {
//        newsRepository.deleteById(id);
//    }
//}
