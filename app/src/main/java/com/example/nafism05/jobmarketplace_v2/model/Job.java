package com.example.nafism05.jobmarketplace_v2.model;

import java.util.HashMap;
import java.util.Map;

public class Job {
    private String id;
    private String user_id;
    private String title;
    private String language;
    private String detail;

    public Job() {
    }

    public Job(String id, String user_id, String title, String language, String detail) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.language = language;
        this.detail = detail;
    }

    public Job(String user_id, String title, String language, String detail) {
        this.user_id = user_id;
        this.title = title;
        this.language = language;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("user_id", this.user_id);
        result.put("title", this.title);
        result.put("language", this.language);
        result.put("detail", this.detail);

        return result;
    }
}
