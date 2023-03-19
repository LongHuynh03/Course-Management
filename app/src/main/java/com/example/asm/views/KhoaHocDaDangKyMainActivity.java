package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.GridView;

import com.example.asm.R;
import com.example.asm.adapters.CourseAdapter;
import com.example.asm.models.Courses;
import com.example.asm.services.CourseService;

import java.util.ArrayList;

public class KhoaHocDaDangKyMainActivity extends AppCompatActivity {

    GridView gridView;
    private CourseAdapter adapter;
    ArrayList<Courses> listUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa_hoc_da_dang_ky_main);
        gridView = findViewById(R.id.gvKhoaHocDaDangKy);

    }

    private  void onGetCourse(){
        Intent intent = new Intent(KhoaHocDaDangKyMainActivity.this, CourseService.class);
        intent.setAction(CourseService.COURSE_SERVICE_ACTION_GET);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGetCourse();
        IntentFilter intentFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Courses> list = (ArrayList<Courses>) intent.getSerializableExtra("result");
            adapter = new CourseAdapter(list);
            listUpdate = list;
            for (int i = 0; i<list.size(); i++){
                System.out.println(i + ": " + list.get(i).getId() + "\n" + list.get(i).getName());
            }
            gridView.setAdapter(adapter);
        }
    };
}