package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.services.UserService;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText Email = findViewById(R.id.edtEmailSignUp);
        EditText Name = findViewById(R.id.edtNameSignUp);
        EditText Pass = findViewById(R.id.edtPassSignUp);
        RadioButton rdAdmin = findViewById(R.id.rdAdmin);
        RadioButton rdUser = findViewById(R.id.rdUser);
        Button Dangki = findViewById(R.id.btnSignUp);
        Button DangNhap = findViewById(R.id.btnChangeLogin);

        Dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = Email.getText().toString();
                    String name = Name.getText().toString();
                    String pass = Pass.getText().toString();
                    int role = 0;
                    if (rdAdmin.isChecked()){
                        role = 1;
                    }
                    if (rdUser.isChecked()){
                        role = 2;
                    }
                    Intent intent = new Intent(SignUpActivity.this, UserService.class);
                    intent.setAction(UserService.ACTION_REGISTER);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("pass",pass);
                    intent.putExtra("role",role);
                    startService(intent);
                    System.out.println(" SignUpActivity \n email: "+email + "\nMK: "+pass);
                }catch (Exception e){
                    System.out.println("Lỗi :" + e);
                }
//                Intent intent = new Intent(SignUpActivity.this, ManHinhChao.class);
//                startActivity(intent);
//                finish();
            }
        });

        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, ManHinhChao.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean result = intent.getBooleanExtra("result",false);
            if (result){
                Toast.makeText(context, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                System.out.println("halo");
                finish();
                Intent intent1 = new Intent(SignUpActivity.this, ManHinhChao.class);
                startActivity(intent1);
            }
            else {
                Toast.makeText(context, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    };
}