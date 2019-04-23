package com.mobileinternshiptask;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String nickname;
    private List<Repository> listOfRepositories = new LinkedList<>();

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


}