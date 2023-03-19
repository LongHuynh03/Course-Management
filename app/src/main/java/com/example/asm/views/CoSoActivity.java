package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.asm.R;

public class CoSoActivity extends AppCompatActivity {
    String diachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_so);
        TextView tvCsHN, tvCsDN, tvCsTN, tvCsHCM, tvCsCT;
        tvCsHN = findViewById(R.id.tvCsHN);
        tvCsDN = findViewById(R.id.tvCsDN);
        tvCsTN = findViewById(R.id.tvCsTN);
        tvCsHCM = findViewById(R.id.tvCsHCM);
        tvCsCT = findViewById(R.id.tvCsCT);

        tvCsHN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diachi = "HN";
                getActivity(diachi);
            }
        });

        tvCsDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diachi = "DN";
                getActivity(diachi);
            }
        });

        tvCsTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diachi = "TN";
                getActivity(diachi);
            }
        });

        tvCsHCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diachi = "HCM";
                getActivity(diachi);
            }
        });

        tvCsCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diachi = "CT";
                getActivity(diachi);
            }
        });
    }

    public void getActivity(String dc){
        Intent intent = new Intent(CoSoActivity.this,MapActivity.class);
        intent.putExtra("diachi",dc);
        startActivity(intent);
    }
}