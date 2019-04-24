package com.mobileinternshiptask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;

public class RepositoryDetailsActivity extends AppCompatActivity {

    Repository repository;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            repository = (Repository) extras.getSerializable("repository");
        }
        TextView textView = (TextView)findViewById(R.id.repo_details);
        String detailsString = "name: " + repository.getName()+ "\n"
                +"created at: " +repository.getCreatedAt()+ "\n"
                +"updated at: "+repository.getUpdatedAt()+"\n"
                +"forks: "+ String.valueOf(repository.getFokrs())+"\n"
                +"language: "+repository.getLanguage();
        textView.setText(detailsString);

        Toast.makeText(getBaseContext(), repository.getName(), Toast.LENGTH_SHORT).show();
    }
}
