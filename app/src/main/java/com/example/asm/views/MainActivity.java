package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapters.AppAdapter;
import com.example.asm.adapters.CourseAdapter;
import com.example.asm.models.Courses;
import com.example.asm.services.CourseService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnLogOut;
    int cachLogin;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        cachLogin = intent.getIntExtra("cachLogin", 0);
        System.out.println(cachLogin + "");

        GridView gv = findViewById(R.id.gridView);
        AppAdapter adapter = new AppAdapter(this);
        btnLogOut = findViewById(R.id.btnLogOut);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(MainActivity.this,googleSignInOptions);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogOut();
            }
        });



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int po = position;
//                System.out.println("vị trí : "+po);
                if (po == 0){
                    Intent intent = new Intent(MainActivity.this,CoursesActivity.class);
                    startActivity(intent);
                }
                if (po == 1){
                    Intent intent = new Intent(MainActivity.this,CoSoActivity.class);
                    startActivity(intent);
                }
                if (po == 2){
                    Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                    startActivity(intent);
                }
            }
        });
        gv.setAdapter(adapter);
    }

    public void onLogOut() {
        if (cachLogin == 1){
            Intent intent = new Intent(MainActivity.this, ManHinhChao.class);
            startActivity(intent);
            finish();
        }
        if (cachLogin == 2){
            googleSignInClient.signOut().addOnCanceledListener(this, new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    System.out.println("Lỗi logOut gg");
                }
            });
            googleSignInClient.signOut().addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Intent intent1 = new Intent(MainActivity.this, ManHinhChao.class);
                    startActivity(intent1);
                    finish();
                }
            });
        }
        if (cachLogin == 3){
            Intent intent = new Intent(MainActivity.this, ManHinhChao.class);
            startActivity(intent);
            finish();
        }
    }
}