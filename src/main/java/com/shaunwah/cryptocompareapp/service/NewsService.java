package com.shaunwah.cryptocompareapp.service;

import com.shaunwah.cryptocompareapp.model.Article;
import com.shaunwah.cryptocompareapp.repository.NewsRepository;
import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.util.*;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Value("${cryptocompare.api.lang}")
    private String cryptocompareApiLang;

    @Value("${cryptocompare.api.key}")
    private String cryptocompareApiKey;

    public void saveArticle(Article article) {
        newsRepository.saveArticle(article.toJson());
    }

    public void saveArticles(List<Article> articles) {
        for (Article article: articles) {
            saveArticle(article);
        }
    }

    public Optional<Article> retrieveArticle(String id) {
        String result = newsRepository.retrieveArticle(id);
        if (result != null) {
            JsonReader jr = Json.createReader(new StringReader(result));
            JsonObject obj = jr.readObject();
            return Optional.of(Article.fromJson(obj));
        }
        return Optional.empty();
    }

    public Optional<List<Article>> getExternalArticles() {
        String url = UriComponentsBuilder
                .fromUriString("https://min-api.cryptocompare.com/data/v2/news/")
                .queryParam("lang", cryptocompareApiLang)
                .queryParam("api_key", cryptocompareApiKey)
                .toUriString();

        RequestEntity<Void> request = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(request, String.class);

        if (response.hasBody()) {
            List<Article> articles = new LinkedList<>();
            JsonReader jr = Json.createReader(new StringReader(response.getBody()));
            JsonObject obj = jr.readObject();
            JsonArray jsonArticles = obj.getJsonArray("Data");

            for (JsonValue jsonArticle: jsonArticles) {
                Article article = Article.fromJson(jsonArticle.asJsonObject());
                articles.add(article);
            }

            return Optional.of(articles);
        }
        return Optional.empty();
    }
}
