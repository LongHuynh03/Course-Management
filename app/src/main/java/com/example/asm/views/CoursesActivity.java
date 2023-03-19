package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.asm.R;
import com.example.asm.adapters.AppAdapter;
import com.example.asm.adapters.CoursesAdapter;

public class CoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        GridView gv = findViewById(R.id.gridViewCourse);
        CoursesAdapter adapter = new CoursesAdapter(this);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int po = position;
//                System.out.println("vị trí : "+po);
                if (po == 0){
                    Intent intent = new Intent(CoursesActivity.this,DanhSachKhoaHocActivity.class);
                    startActivity(intent);
                }
                if (po == 1){
                    Intent intent = new Intent(CoursesActivity.this,DanhSachKhoaHocActivity.class);
                    startActivity(intent);
                }
            }
        });
        gv.setAdapter(adapter);

    }
}