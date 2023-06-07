package com.shaunwah.cryptocompareapp.controller;

import com.shaunwah.cryptocompareapp.model.Article;
import com.shaunwah.cryptocompareapp.service.NewsService;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping(path = "/",
            produces = MediaType.TEXT_HTML_VALUE)
    public String getArticles(Model model) {
        Optional<Map<String, Article>> articles = newsService.getExternalArticles();

        if (articles.isPresent()) {
            model.addAttribute("articles", articles.get());
        }

        return "articles";
    }

    @PostMapping(path = "/articles",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String saveArticles(@RequestBody MultiValueMap<String, String> requestBody) {
        Map<String, Article> articles = requestBody.toSingleValueMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(k -> k.getKey(), v -> {
                    JsonReader jr = Json.createReader(new StringReader(v.getValue()));
                    return Article.fromJson(jr.readObject());
                }));
        newsService.saveArticles(articles);
        return "articlesSaved";
    }
}
