package com.mobileinternshiptask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.List;

public class ListOfRepositoriesActivity extends AppCompatActivity {
    String nickname;
    List<String> repositoriesNamesList = new ArrayList<>();
    User user;
    ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_repositories);
        Bundle extras = getIntent().getExtras();
        repositoriesNamesList.clear();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
            for (int i = 0; i < user.getListOfRepositories().size(); i++) {
                repositoriesNamesList.add(user.getListOfRepositories().get(i).getName());
            }
        }
        listView = (ListView) findViewById(R.id.repos_list);
        listView.setAdapter(null);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.row, repositoriesNamesList);
        //listView.setAdapter(new ArrayAdapter<String>(this, R.layout.row, repositoriesNamesList));
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (listView.getItemAtPosition(position).toString());
                //Toast.makeText(getBaseContext(), selectedFromList, Toast.LENGTH_SHORT).show();
                Repository repository = new Repository();
                repository = user.getListOfRepositories().get(position);
                String date = repository.getUpdatedAt();
                Toast.makeText(getBaseContext(), date, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RepositoryDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("repository", repository);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


    private static String githubReposStringPath(String nickname) {
        return ("https://api.github.com/users/" + nickname + "/repos");
    }

//    @Override
//    public void onBackPressed() {
////        repositoriesNamesList.clear();
////        arrayAdapter.notifyDataSetChanged();
////        List<String> emptyList = new ArrayList<>();
//        listView.setAdapter(null);
//        finish();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //isNicknameExisting = false;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            listView.setAdapter(null);
        }
        finish();
        return false;
    }
}
