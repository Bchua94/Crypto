package com.shaunwah.cryptocompareapp.controller;

import com.shaunwah.cryptocompareapp.model.Article;
import com.shaunwah.cryptocompareapp.service.NewsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/news",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsRestController {
    @Autowired
    private NewsService newsService;
    @GetMapping(path = "{id}")
    public ResponseEntity<String> getArticle(@PathVariable String id) {
        Optional<Article> article = newsService.retrieveArticle(id);
        if (article.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(article.get().toJson().toString());
        }
        String message = "{ 'error': 'Cannot find news article %s' }";
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new JSONObject(message.formatted(id)).toString());
    }
}
