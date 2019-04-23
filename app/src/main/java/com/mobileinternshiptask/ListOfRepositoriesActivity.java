package com.mobileinternshiptask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListOfRepositoriesActivity extends AppCompatActivity {
    String nickname;
    List<String> repositoriesList = new ArrayList<>();
    User user;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_repositories);
        TextView textView = (TextView) findViewById(R.id.text);
        TextView tekstListaRepozytorow = (TextView) findViewById(R.id.teks_lista_repozytorow);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nickname = extras.getString("nickname");
            textView.setText(nickname);
            user = new User(nickname);
        }
        //user.getListOfRepositories();
        String stringtmpLisa = "";
        for (int i = 0; i < repositoriesList.size(); i++) {
            stringtmpLisa = stringtmpLisa + " " + repositoriesList.get(i);
        }
        tekstListaRepozytorow.setText(stringtmpLisa);

        dowwnloadUserRepositories(user,this);
    }

    public void dowwnloadUserRepositories(User user, final ListOfRepositoriesActivity activity) {
        final String nickname = user.getNickname();
        String url = githubReposStringPath(nickname);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int x = 5+2;
                        int y = x;
//                        try {
////                            List<String> list = new ArrayList<>();
////                            JSONArray jsonArray = response.getJSONArray();
////                            for (int i = 0; i < jsonArray.length(); i++) {
////                                JSONObject employee = jsonArray.getJSONObject(i);
////                                list.add(employee.getString("name"));
////                            }
//
//                            // String firstName = employee.getString("firstname");
//                            //int age = employee.getInt("age");
//                            //String mail = employee.getString("mail");
//                        //    String name = response.getString("name");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        System.out.println("nk");
        mQueue.add(request);

    }

    private static String githubReposStringPath(String nickname) {
        return ("https://api.github.com/users/" + nickname + "/repos");
    }


}
