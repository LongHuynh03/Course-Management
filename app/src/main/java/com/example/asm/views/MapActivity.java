package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.asm.R;
import com.example.asm.fragment.MapsFragment;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        String diaChi = intent.getStringExtra("diachi");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flMap, MapsFragment.newInstance(diaChi))
                .commit();
    }
}