package com.mobileinternshiptask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String nickname;
    private List<Repository> listOfRepositories = new ArrayList<>();

    public List<Repository> getListOfRepositories() {
        return listOfRepositories;
    }

    public void setListOfRepositories(List<Repository> listOfRepositories) {
        this.listOfRepositories = listOfRepositories;
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public User(String nickname, List<Repository> listOfRepositories) {
        this.nickname = nickname;
        this.listOfRepositories = listOfRepositories;
    }
}