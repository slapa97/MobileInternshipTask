package com.mobileinternshiptask;

import android.icu.util.LocaleData;
import android.os.AsyncTask;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Repository  implements Serializable {
    private String name; /// "name"
    private String language; /// "language"
    private int fokrs; /// "forks"
    private String createdAt; /// "created_at"
    private String updatedAt; /// "updated_at"

    public Repository(String name, String language, int fokrs, String createdAt, String updatedAt)  {
        this.name = name;
        this.language = language;
        this.fokrs = fokrs;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public  Repository(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setFokrs(int fokrs) {
        this.fokrs = fokrs;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public int getFokrs() {
        return fokrs;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
