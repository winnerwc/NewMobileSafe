package com.example.mobilesafe.UI;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.widght.SlideSwitch;

public class SettingActivity extends Activity implements SlideSwitch.OnSwitchChangedListener {

    private SlideSwitch msgSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initView();
    }

    private void initView() {
        msgSwitch = findViewById(R.id.msg_slideSwitch);
        msgSwitch.setOnSwitchChangedListener(this);
    }

    @Override
    public void onSwitchChanged(SlideSwitch obj, int status) {
        switch (obj.getId()){
            case R.id.msg_slideSwitch:
                Toast.makeText(this, "slideSwitch状态" + status, Toast.LENGTH_SHORT).show();
                  break;
            default:
                  break;
        }
    }
}
