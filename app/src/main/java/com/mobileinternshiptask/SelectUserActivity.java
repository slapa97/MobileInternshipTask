package com.mobileinternshiptask;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class SelectUserActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextView textViewTmpp;
    EditText githubNicknameEditText;
    boolean isNicknameExisting;


    public void goToListOfRepositoriesWindow(View view) {
        doesNicknameExists(this);
    }

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
                            Intent intent = new Intent(activity, ListOfRepositoriesActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("nickname", login);
                            intent.putExtras(bundle);
                            startActivity(intent);
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
