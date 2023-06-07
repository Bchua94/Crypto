package com.shaunwah.cryptocompareapp.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.Serializable;

public class Article implements Serializable {
    public String id;
    public long published_on;
    public String title;
    public String url;
    public String imageurl;
    public String body;
    public String tags;
    public String categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPublished_on() {
        return published_on;
    }

    public void setPublished_on(long published_on) {
        this.published_on = published_on;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("published_on", this.getPublished_on())
                .add("title", this.getTitle())
                .add("url", this.getUrl())
                .add("imageurl", this.getImageurl())
                .add("body", this.getBody())
                .add("tags", this.getTags())
                .add("categories", this.getCategories())
                .build();
    }

    public static Article fromJson(JsonObject o) {
        Article article = new Article();
        article.setId(o.getString("id"));
        article.setPublished_on(o.getJsonNumber("published_on").longValue());
        article.setTitle(o.getString("title"));
        article.setUrl(o.getString("url"));
        article.setImageurl(o.getString("imageurl"));
        article.setBody(o.getString("body"));
        article.setTags(o.getString("tags"));
        article.setCategories(o.getString("categories"));
        return article;
    }
}
