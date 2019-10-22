package com.mobileinternshiptask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeletaDataActivity extends AppCompatActivity {

    private static final String TAG = "DeletaDataActivity";

    private Button btnSave,btnDelete;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1); //  -1 is just the default value
        selectedName = receivedIntent.getStringExtra("name");
        editable_item.setText(selectedName);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
               goToListDataView(view);
            }
        });

    }
    public void goToListDataView(View view) {
        Intent intent = new Intent(this, ListDataActivity.class);
        startActivity(intent);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}