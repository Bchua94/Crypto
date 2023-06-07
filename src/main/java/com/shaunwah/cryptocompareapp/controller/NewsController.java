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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping(path = "/",
            produces = MediaType.TEXT_HTML_VALUE)
    public String getArticles(Model model) {
        Optional<List<Article>> articles = newsService.getExternalArticles();

        if (articles.isPresent()) {
            model.addAttribute("articles", articles.get());
        } else {
            model.addAttribute("articles", new LinkedList<Article>());
        }

        return "articles";
    }

    @PostMapping(path = "/articles",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String saveArticles(@RequestBody MultiValueMap<String, String> requestBody) {
        List<Article> articles = new LinkedList<Article>(requestBody
                .toSingleValueMap()
                .values()
                .stream()
                .map(v -> {
                    JsonReader jr = Json.createReader(new StringReader(v));
                    return Article.fromJson(jr.readObject());
                })
                .toList());
        newsService.saveArticles(articles);
        return "articlesSaved";
    }
}
