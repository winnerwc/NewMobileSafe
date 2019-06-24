package com.example.mobilesafe.UI;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Consts.Consts;
import com.example.entity.Phone;
import com.example.mobilesafe.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class phoneNumberLocation extends AppCompatActivity {

    private EditText mMPhoneNumber;
    private Button mSearch;
    private String TAG = "phoneLocation";
    private PhoneHandler mPhoneHandler = new PhoneHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_location);
        mMPhoneNumber = findViewById(R.id.phoneNumber);
        mSearch = findViewById(R.id.search);
    }

    public void serch(View view) {
        Pattern compile = Pattern.compile("[0-9]*");
        Matcher matcher = compile.matcher(mMPhoneNumber.getText().toString());
        boolean matches = matcher.matches();
        Log.d(TAG, "matcher: " + matcher.matches());
        String number = mMPhoneNumber.getText().toString();
        int length = number.length();
        Log.d(TAG, "phone number length: " +length);
        if (mMPhoneNumber.getText().toString() != null && matches == true && length == 11){
            String url;
            url = Consts.BAIDU_PHONE_URL + "?tel=" + mMPhoneNumber.getText().toString();
            new PhoneThread(url,number).start();
        }else{
            Toast.makeText(this, "请输入正确号码", Toast.LENGTH_SHORT).show();
        }
    }
    class PhoneThread extends Thread{
        private String url;
        private String number;
        private PhoneThread(String url, String number) {
            this.url = url;
            this.number = number;
        }

        @Override
        public void run() {
            String result = getJsonFromURL(url);
            if (TextUtils.isEmpty(result)){
                Looper.prepare();
                Toast.makeText(phoneNumberLocation.this, "无效号码", Toast.LENGTH_SHORT).show();
            }else {
                Phone phone = parseJsonToPhone(result, number);
                Message msg = Message.obtain();
                msg.obj = phone;
                mPhoneHandler.sendMessage(msg);

            }
        }
    }

    private Phone parseJsonToPhone(String result, String number) {
        Phone phone = new Phone();
        try {
            JSONObject object = new JSONObject(result);
            JSONObject numberObj = object.getJSONObject("response").getJSONObject(number);
            JSONObject detailObj = numberObj.getJSONObject("detail");
            phone.setNumber(number);
            phone.setCarrier(detailObj.getString("operator"));
            phone.setOwnerCarrier(numberObj.getString("location"));
            phone.setProvince(detailObj.getString("province"));
            Log.d(TAG, "parseJsonToPhone:  number is :" + phone.getNumber() + " carrier is :" + phone.getCarrier() + " Location is :" +phone.getOwnerCarrier() +
                    " province is :" +phone.getProvince());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return phone;
    }

    private String getJsonFromURL(String url) {
        StringBuilder sb = new StringBuilder();
        try {
            Log.d(TAG, "getJsonFromURL: " +url);
            InputStream is = new URL(url).openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr);
            String line;
            while ((line = buffer.readLine()) != null){
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getJsonFromURL: " + sb.toString());
        return sb.toString();
    }
    @SuppressLint("HandlerLeak")
    class PhoneHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Phone phone = (Phone) msg.obj;
            showInfo(phone);
        }
    }

    private void showInfo(Phone phone) {
    }
}
