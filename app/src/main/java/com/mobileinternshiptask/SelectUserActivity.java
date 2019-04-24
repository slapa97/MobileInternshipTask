package com.mobileinternshiptask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class SelectUserActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView textViewTmpp;
    private EditText githubNicknameEditText;
    private User user = null;
    private ArrayList<Repository> listOfRepositories = new ArrayList<>();

    public void goToListOfRepositoriesWindow(View view) throws InterruptedException {
        doesNicknameExists(this);
        downloadRepositories(this);
    }


    private void downloadRepositories(final SelectUserActivity activity) {

        final String nickname = githubNicknameEditText.getText().toString();
        String urlRepos = githubReposStringPath(nickname);
        listOfRepositories.clear();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlRepos, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject ob = response.getJSONObject(i);
                        Repository repository = new Repository();
                        repository.setName(ob.getString("name"));
                        repository.setCreatedAt(ob.getString("created_at"));
                        repository.setFokrs(ob.getInt("forks"));
                        repository.setLanguage(ob.getString("language"));
                        repository.setUpdatedAt(ob.getString("updated_at"));
                        listOfRepositories.add(repository);
                    }
                    Intent intent = new Intent(activity, ListOfRepositoriesActivity.class);
                    Bundle bundle = new Bundle();
                    user = new User("user", listOfRepositories);
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                textViewTmpp.setText("");
                textViewTmpp.append("User " + nickname + " doesn't' exist");
            }
        });
        mQueue.add(request);
    }

    private void doesNicknameExists(final SelectUserActivity activity) {

        final String nickname = githubNicknameEditText.getText().toString();
        String url = githubUsersStringPath(nickname);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String login = response.getString("login");
                            user = new User(login);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                textViewTmpp.setText("");
                textViewTmpp.append("User " + nickname + " doesn't' exist");
            }
        });
        mQueue.add(request);
    }


    private static String githubUsersStringPath(String nickname) {
        return ("https://api.github.com/users/" + nickname);
    }

    private static String githubReposStringPath(String nickname) {
        return ("https://api.github.com/users/" + nickname + "/repos");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        final Button searchButton = (Button) findViewById(R.id.search_button);
        githubNicknameEditText = (EditText) findViewById(R.id.github_nickname_edit_text);
        textViewTmpp = (TextView) findViewById(R.id.textViewtmp);
        mQueue = Volley.newRequestQueue(this);
    }
}
