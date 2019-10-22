package com.mobileinternshiptask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
    DatabaseHelper databaseHelper;
    SharedPref sharedPref;


    private Switch switchNight;
    private CheckBox checkBoxNight;
    private Button buttonHistory;

    public void goToListOfRepositoriesWindow(View view) throws InterruptedException {
        doesNicknameExists(this);
        downloadRepositories(this);
        addNicknametoDatabase();
    }

    public void addNicknametoDatabase() {
        String newEntry = githubNicknameEditText.getText().toString();
        if (githubNicknameEditText.length() != 0) {
            AddData(newEntry);
        } else {
            toastMessage("You must put something in the text field!");
        }
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


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState() == true) {
            setTheme(R.style.ThemeOverlay_AppCompat_Dark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        final Button searchButton = (Button) findViewById(R.id.search_button);
        githubNicknameEditText = (EditText) findViewById(R.id.github_nickname_edit_text);
        textViewTmpp = (TextView) findViewById(R.id.textViewtmp);
        mQueue = Volley.newRequestQueue(this);
        databaseHelper = new DatabaseHelper(this);
        Switch switchNight = (Switch) findViewById(R.id.switch1);

//        Intent intent = new Intent(My);
//        // Check if you have the right broadcast intent
//        if (intent.getAction().equals("android.intent.action.AIRPLANE_MODE")) {
//            // Get all extras
//            Bundle extras = intent.getExtras();
//
//            // Fetch the boolean extra using getBoolean()
//            boolean state = extras.getBoolean("state");
//
//            // Log the value of the extra
//            Log.d("MYAPP", "AIRPLANE MODE: " + state);
//        }


        if (sharedPref.loadNightModeState()==true) {
            switchNight.setChecked(true);
        }
        switchNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPref.setNightModeState(true);
                    restartApp();
                } else {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });
    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), SelectUserActivity.class);
        startActivity(i);
        finish();
    }

    public void goToListDataView(View view) {
        Intent intent = new Intent(this, ListDataActivity.class);
        startActivity(intent);
    }

    public void AddData(String newEntry) {
        boolean insertData = databaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
}
