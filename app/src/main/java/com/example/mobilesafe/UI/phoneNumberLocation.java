package com.example.mobilesafe.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilesafe.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class phoneNumberLocation extends AppCompatActivity {

    private EditText mPhoneNumber;
    private Button mSearch;
    private String TAG = "phoneNumberLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_location);
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mSearch = findViewById(R.id.search);
    }

    public void serch(View view) {
        Pattern compile = Pattern.compile("[0-9]*");
        Matcher matcher = compile.matcher(mPhoneNumber.getText().toString());
        boolean matches = matcher.matches();
        Log.d(TAG, "matcher: " + matcher.matches());
        String number = mPhoneNumber.getText().toString();
        int length = number.length();
        Log.d(TAG, "phone number length: " +length);
        if (mPhoneNumber.getText().toString() != null && matches == true && length == 11){
            Toast.makeText(this, "请等待结果", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "请输入正确号码", Toast.LENGTH_SHORT).show();
        }
    }
}
