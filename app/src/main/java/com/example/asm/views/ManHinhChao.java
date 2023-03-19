package com.example.asm.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.DAO.UserDAO;
import com.example.asm.R;
import com.example.asm.models.User;
import com.example.asm.services.UserService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManHinhChao extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    SignInButton signInButton;
    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        CountDownTimer timer =new CountDownTimer(2000,2000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                DialogLogin();
            }
        };
        timer.start();
    }
    public void loginGmail(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(ManHinhChao.this,googleSignInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Toast.makeText(this, "Đã LogIn google", Toast.LENGTH_SHORT).show();
        }
    }

    String passGG;
    String emailGG;
    String nameGG;

    ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        passGG = account.getId();
                        emailGG = account.getEmail();
                        nameGG = account.getDisplayName();
                        System.out.println("Email: "+emailGG + "\n pass: "+ passGG + "\n name: "+nameGG);
                        UserDAO userDAO = new UserDAO(ManHinhChao.this);
                        List<User> list = userDAO.getAll();

                        int check = 0;
                        for (int i = 0; i< list.size();i++){
                            System.out.println(list.get(i).getEmail());
                        }
                        for (int i = 0; i< list.size();i++){
                            if (emailGG.equals(list.get(i).getEmail())){
                                check = -1;
                                break;
                            }
                        }
                        if (check >= 0 ){
                            Intent intent = new Intent(ManHinhChao.this, UserService.class);
                            intent.setAction(UserService.ACTION_REGISTER);
                            intent.putExtra("name",nameGG);
                            intent.putExtra("email",emailGG);
                            intent.putExtra("pass",passGG);
                            intent.putExtra("role",2);
                            startService(intent);
                            loginGG();
                        }
                        else {
                            loginGG();
                        }
                    } catch (ApiException e) {
                        System.out.println("lỗi: "+ e);
                    }
                }
            }
    );
    int cachLogin = 0;

    public void loginGG(){
        Intent intent = new Intent(ManHinhChao.this, UserService.class);
        intent.setAction(UserService.ACTION_LOGIN);
        intent.putExtra("email",emailGG);
        intent.putExtra("password",passGG);
        startService(intent);
    }

    private AlertDialog alertDialog;
    public void DialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_login,null);
        builder.setView(dialog);
        EditText Email = dialog.findViewById(R.id.edtEmailLogin);
        EditText Pass = dialog.findViewById(R.id.edtPassLogin);
        Button DangNhap = dialog.findViewById(R.id.btnLogin);
        Button DangKi = dialog.findViewById(R.id.btnChangeSignUp);

        loginButton = (LoginButton) dialog.findViewById(R.id.login_button);

        signInButton = dialog.findViewById(R.id.btnGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                googleLauncher.launch(intent);
                cachLogin = 2;
            }
        });

        loginGmail();
        btnLoginFaceBook();

        DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String email = Email.getText().toString();
                    String pass = Pass.getText().toString();
                    cachLogin = 1;

                    Intent intent = new Intent(ManHinhChao.this, UserService.class);
                    intent.setAction(UserService.ACTION_LOGIN);
                    intent.putExtra("email",email);
                    intent.putExtra("password",pass);
                    startService(intent);
                }
                catch (Exception e){
                    System.out.println("Lỗi : "  + e);
        }
            }
        });


        DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhChao.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        builder.create();
        alertDialog = builder.setCancelable(false).show();
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
            String s = "";
            User user = (User) intent.getSerializableExtra("appUser");
            if(user != null){
                alertDialog.dismiss();
                s = "Đăng nhập thành công";
                Intent homeIntent = new Intent(ManHinhChao.this, MainActivity.class);
                homeIntent.putExtra("cachLogin",cachLogin);
                startActivity(homeIntent);
                finish();
            }
            else {
                s = "Đăng nhập thất bại";
            }
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    };

    public void btnLoginFaceBook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("onSuccess getInstance: " + loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError getInstance: " + exception.getMessage());

                    }
                });
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess btnlogin: " + loginResult.getAccessToken());
//                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject jsonObject,GraphResponse graphResponse) {
//                                System.out.println(graphResponse.getJSONObject());
////                                JSONObject object = graphResponse.getJSONObject();
//                                try {
//                                    if (jsonObject != null){
////                                        String id = jsonObject.getString("id");
////                                        String name = jsonObject.getString("name");
//                                        String email = jsonObject.getString("email");
//
////                                        System.out.println("FaceBook: \nid: "+id +"\nname: "+ name + "\n email: "+email);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "email");
//                request.setParameters(parameters);
//                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError btnlogin: " + exception.getMessage());
            }
        });


        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(@Nullable Profile profile, @Nullable Profile profile1) {
                if (profile1 != null) {
                    passGG = profile1.getId();
                    nameGG = profile1.getName();
                    emailGG = profile1.getFirstName() + "@gmail.com";
                    System.out.println("Email: "+passGG + "\n pass: "+ nameGG + "\n name: "+emailGG);

                    UserDAO userDAO = new UserDAO(ManHinhChao.this);
                    List<User> list = userDAO.getAll();

                    int check = 0;
                    for (int i = 0; i< list.size();i++){
                        System.out.println(list.get(i).getEmail());
                    }
                    for (int i = 0; i< list.size();i++){
                        if (emailGG.equals(list.get(i).getEmail())){
                            check = -1;
                            break;
                        }
                    }
                    if (check >= 0 ){
                        Intent intent = new Intent(ManHinhChao.this, UserService.class);
                        intent.setAction(UserService.ACTION_REGISTER);
                        intent.putExtra("name",nameGG);
                        intent.putExtra("email",emailGG);
                        intent.putExtra("pass",passGG);
                        intent.putExtra("role",2);
                        startService(intent);
                        cachLogin = 3;
                        loginGG();
                    }else {
                        loginGG();
                        cachLogin = 3;
                    }

                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}