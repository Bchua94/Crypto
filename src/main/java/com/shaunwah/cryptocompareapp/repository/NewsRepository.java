package com.shaunwah.cryptocompareapp.repository;

import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class NewsRepository {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private final String KEY = "ARTICLES";

    public void saveArticle(JsonObject article) {
        redisTemplate.opsForHash().put(KEY, article.getString("id"), article.toString());
    }

    public String retrieveArticle(String id) {
        return (String) redisTemplate.opsForHash().get(KEY, id);
    }

    public List<String> retrieveArticles() {
        Map<Object, Object> result = redisTemplate.opsForHash().entries(KEY);
        return result.values()
                .stream()
                .map(v -> (String) v)
                .toList();
    }
}
