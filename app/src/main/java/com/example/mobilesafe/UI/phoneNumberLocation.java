package com.example.mobilesafe.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilesafe.R;

public class phoneNumberLocation extends AppCompatActivity {

    private EditText mPhoneNumber;
    private Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_location);
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mSearch = findViewById(R.id.search);
    }

    public void serch(View view) {
        if (mPhoneNumber.getText().toString() != null){

        }
    }
}
