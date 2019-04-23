package com.mobileinternshiptask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.textclassifier.TextLinks;
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
import java.util.LinkedList;
import java.util.List;

public class SelectUserActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView textViewTmpp;
    EditText githubNicknameEditText;
    boolean isNicknameExisting;
    private User user = null;
    private ArrayList<Repository> listOfRepositories = new ArrayList<>();

    public void goToListOfRepositoriesWindow(View view) throws InterruptedException {
        //      doesNicknameExists(this);
        //Thread.sleep(10000);
        downloadRepositories(this);
    }

    private void downloadRepositories(final SelectUserActivity activity) {
        User user = new User("ziomek");
        Repository repository = new Repository();
        repository.setName("pierwsze");
        repository.setCreatedAt("11.11.1111");
        repository.setFokrs(12);
        repository.setLanguage("java");
        repository.setUpdatedAt("11.11.1111");


        Repository repository2 = new Repository();
        repository2.setName("drugie");
        repository2.setCreatedAt("22.2.222");
        repository2.setFokrs(99);
        repository2.setLanguage("c++");
        repository2.setUpdatedAt("23.2.222");


        Repository repository3 = new Repository();
        repository3.setName("trzecie");
        repository3.setCreatedAt("33.33.3333");
        repository3.setFokrs(33);
        repository3.setLanguage("c+++");
        repository3.setUpdatedAt("33.33.3333");
        listOfRepositories.add(repository);
        listOfRepositories.add(repository2);
        listOfRepositories.add(repository3);



        user.setListOfRepositories(listOfRepositories);

        Intent intent = new Intent(activity, ListOfRepositoriesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);


    }

//    private void downloadRepositories(final SelectUserActivity activity) {
//
//        final String nickname = githubNicknameEditText.getText().toString();
//        String urlRepos = githubReposStringPath(nickname);
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlRepos, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject ob = response.getJSONObject(i);
//                        Repository repository = new Repository();
//                        repository.setName(ob.getString("name"));
//                        repository.setCreatedAt(ob.getString("created_at"));
//                        repository.setFokrs(ob.getInt("forks"));
//                        repository.setLanguage(ob.getString("language"));
//                        repository.setUpdatedAt(ob.getString("updated_at"));
//                        listOfRepositories.add(repository);
//
//
//                    }
//                    user.setListOfRepositories(listOfRepositories);
//                    textViewTmpp.setText(listOfRepositories.get(1).getName());
//
////                    Intent intent = new Intent(activity, ListOfRepositoriesActivity.class);
////                    Bundle bundle = new Bundle();
////                    bundle.putSerializable("user", user);
////                    intent.putExtras(bundle);
////                    startActivity(intent);
//
//                    Intent intent = new Intent(activity, ListOfRepositoriesActivity.class);
//                    Bundle bundle = new Bundle();
//                    user = new User("dupa");
//                    user.setListOfRepositories(listOfRepositories);
//                    bundle.putSerializable("user", user);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                textViewTmpp.setText("");
//                textViewTmpp.append("User " + nickname + " doesn't' exist");
//            }
//        });
//        mQueue.add(request);
//    }

    private boolean doesNicknameExists(final SelectUserActivity activity) {

        final String nickname = githubNicknameEditText.getText().toString();
        String url = githubUsersStringPath(nickname);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String login = response.getString("login");
                            isNicknameExisting = true;
                            //   Intent intent = new Intent(activity, ListOfRepositoriesActivity.class);
                            //   Bundle bundle = new Bundle();
                            user = new User(login);
                            //    bundle.putSerializable("user", user);
                            //  intent.putExtras(bundle);
                            //  startActivity(intent);
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

        return isNicknameExisting;
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
        isNicknameExisting = false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isNicknameExisting = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        isNicknameExisting = false;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
