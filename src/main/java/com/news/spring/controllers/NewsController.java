package com.news.spring.controllers;

import com.news.spring.dto.NewsDto;
import com.news.spring.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsCRUDService newsCRUDService;

    public NewsController(NewsCRUDService newsCRUDService) {
        this.newsCRUDService = newsCRUDService;
    }

    @GetMapping("/{id}")
    public NewsDto getNewsById(@PathVariable Long id) {
        return newsCRUDService.getById(id);
    }

    @GetMapping
    public List<NewsDto> getAllNews() {
        return newsCRUDService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto createNews(@RequestBody NewsDto newsDto) {
        newsCRUDService.create(newsDto);
        return newsDto;
    }

    @PutMapping("/{id}")
    public NewsDto updateNews(@PathVariable Long id, @RequestBody NewsDto newsDto) {
        newsCRUDService.update(id, newsDto);
        return newsDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsCRUDService.deleteById(id);
    }
}
