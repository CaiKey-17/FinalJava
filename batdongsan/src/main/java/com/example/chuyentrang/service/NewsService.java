package com.example.chuyentrang.service;

import com.example.chuyentrang.model.*;
import com.example.chuyentrang.repository.AvailableRepository;
import com.example.chuyentrang.repository.ImageLandRepository;
import com.example.chuyentrang.repository.ImageNewsRepository;
import com.example.chuyentrang.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;



    public News createNews(News news) {
        return newsRepository.save(news);
    }


    public Optional<News> getNewsById(int id) {
        return newsRepository.findById(id);
    }

    public Iterable<News> getAllNewss() {
        return newsRepository.findAll();
    }


    public News updateNews(int id, News updatedNews) {
        return newsRepository.findById(id)
                .map(existingNews -> {

                    return newsRepository.save(existingNews);
                }).orElseThrow(() -> new RuntimeException("News not found"));
    }


    public void deleteNews(int id) {
        newsRepository.deleteById(id);
    }
    @Autowired
    private AvailableService availableService;

    @Autowired
    private UserService userService;

    @Autowired
    private AvailableRepository availableRepository;
    @Autowired
    private ImageNewsRepository imageNewsRepository;


    public void postedNews(News news, Long userId, int availableId, List<String> imageLinks) {

        Available available = availableRepository.findByOrderIdAndBroker_Id(availableId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Available not found or does not belong to user"));


        if (available.getQuantityAvailable() <= 0) {
            throw new IllegalArgumentException("No available quantity left for posting.");
        }


        available.setQuantityAvailable(available.getQuantityAvailable() - 1);
        availableRepository.save(available);


        User user = userService.findById(userId);
        news.setAvailable(available);
        news.setBroker(user);
        News savedLand = newsRepository.save(news);


        if (imageLinks != null && !imageLinks.isEmpty()) {
            List<ImageNews> imageLands = imageLinks.stream()
                    .map(link -> new ImageNews(link, savedLand))
                    .collect(Collectors.toList());
            imageNewsRepository.saveAll(imageLands);
        }
    }

    public List<News> listLandByBroker(Long userId) {
        return newsRepository.findByBrokerId(userId);
    }

    public News findById(int id) {
        return newsRepository.findById(id).get();
    }



}
