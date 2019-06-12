package com.example.mobilesafe.UI;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.PopWindowAdapter;
import com.example.mobilesafe.R;
import com.example.mobilesafe.adapter.PageAdapter;
import com.example.entity.PopItem;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView tools;
    private TextView optimization;
    private TextView appmanager;
    private ImageView mTransBar;
    private ViewPager mviewPager;
    private View viewTools;
    private View viewOptimization;
    private View viewApplicationmanager;
    private TextView curTxt;
    private ArrayList<View> pageList;
    private ImageView more;
    private PopItem item;
    private ArrayList<PopItem> list = new ArrayList<>();
    private int width;
    private int hight;
    private View view;
    private ListView listView;
    private PopupWindow popupWindow;
    private LinearLayout ly_filemanager;
    private LinearLayout ly_speed;
    private int isopen = 0;
    private LinearLayout mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startImageParam();
        initContentView();
        initPage();
    }

    private void initPage() {
        ly_filemanager = viewTools.findViewById(R.id.ly_filemanager);
        ly_filemanager.setOnClickListener(new toolsClickListener());
        ly_speed = viewTools.findViewById(R.id.ly_speed);
        ly_speed.setOnClickListener(new toolsClickListener());
        mPhoneNumber = findViewById(R.id.phoneNumberLocation);
        mPhoneNumber.setOnClickListener(new toolsClickListener());

    }

    /**
     * 初始化内容页
     */
    private void initContentView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        viewTools = inflater.inflate(R.layout.activitymain_tools,null);
        viewOptimization = inflater.inflate(R.layout.activitymain_optimization,null);
        viewApplicationmanager = inflater.inflate(R.layout.activitymain_applicationmanage,null);
        pageList.add(viewTools);
        pageList.add(viewOptimization);
        pageList.add(viewApplicationmanager);
        mviewPager.setAdapter(new PageAdapter(pageList));
    }

    private void startImageParam() {
        int diswidth = getWindowManager().getDefaultDisplay().getWidth();
        int imgWidth = (int) (diswidth / 4 * 0.75);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTransBar.getLayoutParams();
        params.width = imgWidth+100;
        System.out.println("imgwidth:" + imgWidth);
        System.out.println("diswidth:" + diswidth);
        mTransBar.setLayoutParams(params);
        TranslateAnimation move = new TranslateAnimation(0, (diswidth / 4 - imgWidth) / 2, 0, 0);
        move.setDuration(200);
        move.setFillAfter(true);
        mTransBar.startAnimation(move);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        tools = findViewById(R.id.tv_tools);
        optimization = findViewById(R.id.tv_optimization);
        appmanager = findViewById(R.id.tv_appmanager);
        mTransBar = findViewById(R.id.trans_bar);
        mviewPager = findViewById(R.id.vpager);
        more = findViewById(R.id.more);
        initMoreList();

        tools.setOnClickListener(this);
        optimization.setOnClickListener(this);
        appmanager.setOnClickListener(this);
        mviewPager.setOnClickListener(this);

        mviewPager.setOnPageChangeListener(this);
        pageList = new ArrayList<>();
        curTxt = tools;
        setTextColor(tools);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] location = new  int[2];
                more.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                width = x - 200 ;
                hight = y ;
                System.out.println("x:" +width);
                System.out.println("y:" +hight);
                showPopWindows();
            }
        });
    }

    private void showPopWindows() {
        view = getLayoutInflater().inflate(R.layout.pop_main, null, false);
        listView = view.findViewById(R.id.pup_list);
        PopWindowAdapter adapter = new PopWindowAdapter(getApplicationContext(),list);
        listView.setAdapter(adapter);
        popupWindow = new PopupWindow(view, 250, 500);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this,UserCenterActivity.class));
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_down_out);
                        Toast.makeText(MainActivity.this, "跳转", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,SettingActivity.class));
                        Toast.makeText(MainActivity.this, "跳转", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow = null;
        System.out.println("111");
    }


    /**
     * 初始化弹出菜单的列表
     */
    private void initMoreList() {
        item = new PopItem("用户中心");
        list.add(item);
        item = new PopItem("系统设置");
        list.add(item);
        item = new PopItem("设置项");
        list.add(item);
        item = new PopItem("设置项");
        list.add(item);
        item = new PopItem("设置项");
        list.add(item);
        item = new PopItem("设置项");
        list.add(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tools:
                imgTrans(tools);
                setTextColor(tools);
                mviewPager.setCurrentItem(0);
                break;
            case R.id.tv_optimization:
                imgTrans(optimization);
                setTextColor(optimization);
                mviewPager.setCurrentItem(1);
                break;
            case R.id.tv_appmanager:
                imgTrans(appmanager);
                setTextColor(appmanager);
                mviewPager.setCurrentItem(2);
                break;
                default:
                    break;
        }
    }

    /**
     * 设置切换文本颜色
     * @param endTxt
     */
    private void setTextColor(TextView endTxt) {
        switch (endTxt.getId()){
            case R.id.tv_tools:
                tools.setTextColor(getResources().getColor(R.color.green));
                optimization.setTextColor(getResources().getColor(R.color.white));
                appmanager.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_appmanager:
                appmanager.setTextColor(getResources().getColor(R.color.green));
                optimization.setTextColor(getResources().getColor(R.color.white));
                tools.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_optimization:
                optimization.setTextColor(getResources().getColor(R.color.green));
                tools.setTextColor(getResources().getColor(R.color.white));
                appmanager.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }
    }

    /**
     * 改变动画效果
     * @param endTxt
     */
    private void imgTrans(TextView endTxt) {
        int startMid = curTxt.getLeft() + curTxt.getWidth() / 2;
        int startLeft = startMid - mTransBar.getWidth() / 2;
        int endMid = endTxt.getLeft() + endTxt.getWidth() / 2;
        int endLeft = endMid - mTransBar.getWidth()/2;
        TranslateAnimation move = new TranslateAnimation(startLeft, endLeft, 0, 0);
        System.out.println("startLeft :" + startLeft);
        System.out.println("endLeft :" + endLeft);
        move.setDuration(200);
        move.setFillAfter(true);
        mTransBar.startAnimation(move);
        curTxt = endTxt;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class toolsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ly_filemanager:
                    startActivity(new Intent(MainActivity.this,DirectorListViewActivity.class));
                    break;
                case R.id.ly_speed:
                    openFlash();
                    break;
                case R.id.phoneNumberLocation:
                    startActivity(new Intent(MainActivity.this,phoneNumberLocation.class));
                default:
                    break;
            }
        }
    }

    private void openFlash() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this, "不支持手电筒", Toast.LENGTH_SHORT).show();
        }else {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CameraManager systemService = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                try {
                    if (isopen == 0){
                        systemService.setTorchMode("0",false);
                        isopen = 1;
                        Toast.makeText(this, "手电筒打开", Toast.LENGTH_SHORT).show();
                    }else if (isopen == 1){
                        systemService.setTorchMode("0",true);
                        isopen = 0;
                        Toast.makeText(this, "手电筒关闭", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
