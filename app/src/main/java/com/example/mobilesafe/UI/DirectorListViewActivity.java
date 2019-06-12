package com.example.mobilesafe.UI;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.DirectoryInfo;
import com.example.mobilesafe.R;
import com.example.utils.FileScan;

import java.io.File;
import java.util.ArrayList;

public class DirectorListViewActivity extends Activity implements View.OnClickListener{

    private TextView canuseDmenmery;
    private TextView canusemenmery;
    private ImageButton directoryReturn;
    private TextView currentDirectory;
    private ListView direcctoryListView;
    private FileScan fileScanner;
    private static final String defaultPath = "/sdcard";
    //private DirectoryInfo directoryInfo;
    private String targetFile = null;
    private DirectoryInfo mDirectoryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.directory_listview);
        initView();
    }

    private void initView() {
        canuseDmenmery = findViewById(R.id.canuseSDmenmery);
        canusemenmery = findViewById(R.id.canusemenmery);
        directoryReturn = findViewById(R.id.dialog_save_turnback);
        currentDirectory = findViewById(R.id.dialog_save_path);
        direcctoryListView = findViewById(R.id.dir_listview);
        directoryReturn.setOnClickListener(this);
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(this);
        fileScanner = new FileScan();
        mDirectoryInfo = fileScanner.getFileDirectory(defaultPath);
        getMenmery();
        createListView(mDirectoryInfo);
    }

    private void getMenmery() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        canuseDmenmery.setText("" + (int) (blockSize * availableBlocks)/1024/1024 + "M");
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo =  new ActivityManager.MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        long menmerysice = memoryInfo.availMem;
        canusemenmery.setText("" + menmerysice/1024/1024 +"M");
    }

    private void createListView(final DirectoryInfo directoryInfo) {
        DirectoryListAdapter directoryListAdapter = new DirectoryListAdapter(this, directoryInfo);
        direcctoryListView.setAdapter(directoryListAdapter);
        direcctoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String fileItem = directoryInfo.currentDirectory + "/" + directoryInfo.directoryName.get(position);
                File file = new File(fileItem);
                if (file.isFile()){
                    targetFile = fileItem;
                    Toast.makeText(DirectorListViewActivity.this, "已选文件", Toast.LENGTH_SHORT).show();
                }else {
                    mDirectoryInfo = fileScanner.getFileDirectory(fileItem);
                    createListView(mDirectoryInfo);
                }
            }
        });
        currentDirectory.setText(mDirectoryInfo.currentDirectory);
    }

    @Override
    public void onClick(View view) {
          switch (view.getId()){
              case R.id.back:
                  finish();
                  break;
              case R.id.dialog_save_turnback:
                  if (!mDirectoryInfo.currentDirectory.equals("/sdcard")){
                      mDirectoryInfo = fileScanner.getFileDirectory(mDirectoryInfo.fatherDirectory);
                      createListView(mDirectoryInfo);
                  }
              default:
                  break;
          }
    }

    class DirectoryListAdapter extends BaseAdapter{

        private LayoutInflater mInflater;
        private ArrayList<String>directoryName,childAmount;
        public DirectoryListAdapter(Context context,DirectoryInfo adapterListItem){
             mInflater = LayoutInflater.from(context);
             directoryName = adapterListItem.directoryName;
             childAmount = adapterListItem.childDirectoryContain;
        }

        @Override
        public int getCount() {
            return directoryName == null ? 0 : directoryName.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.directory_list_items, null);
            holder.directoryImage = convertView.findViewById(R.id.directory_item_image);
            holder.directoryName = convertView.findViewById(R.id.directory_item_name);
            holder.directoryChildAmount = convertView.findViewById(R.id.directory_item_childamount);
            holder.directoryName.setText(directoryName.get(position));
            holder.directoryChildAmount.setText(childAmount.get(position));
            holder.directoryImage.setImageResource(R.drawable.ic_dxhome_file_manager);
            return convertView;
        }
        public final class ViewHolder{
            public ImageView directoryImage;
            public TextView directoryName;
            public TextView directoryChildAmount;
        }
    }
}
