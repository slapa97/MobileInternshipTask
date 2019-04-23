package com.mobileinternshiptask;

import android.icu.util.LocaleData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

public class Repository {
    private String name; /// "name"
    private String language; /// "language"
    private int fokrsCount; /// "forks_count"
    private LocaleData createdAt; /// "created_at"
    private LocaleData updatedAt; /// "updated_at"

    public Repository(String name, String language, int fokrsCount, LocaleData createdAt, LocaleData updatedAt) {
        this.name = name;
        this.language = language;
        this.fokrsCount = fokrsCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setFokrsCount(int fokrsCount) {
        this.fokrsCount = fokrsCount;
    }

    public void setCreatedAt(LocaleData createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocaleData updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public int getFokrsCount() {
        return fokrsCount;
    }

    public LocaleData getCreatedAt() {
        return createdAt;
    }

    public LocaleData getUpdatedAt() {
        return updatedAt;
    }
}
