package com.example.asm.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asm.DAO.UserDAO;
import com.example.asm.models.User;

public class UserService extends IntentService {

    public static final String ACTION_LOGIN = "login";
    public static final String USER_SERVICE_EVENT = "USER_SERVICE_EVENT";
    public static final String ACTION_REGISTER = "ACTION_REGISTER";

    private UserDAO userDAO;

    public UserService() {
        super("UserService");
        userDAO = new UserDAO(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            switch (action){
                case ACTION_LOGIN:{
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("password");
                    User appUser = userDAO.login(email,password);
                    Intent outIntent = new Intent(USER_SERVICE_EVENT);
                    outIntent.putExtra("appUser",appUser);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case ACTION_REGISTER:{
                    String name = intent.getStringExtra("name");
                    String email = intent.getStringExtra("email");
                    String password = intent.getStringExtra("pass");
                    Integer role = intent.getIntExtra("role",1);
                    Boolean result = userDAO.register(name, email,password,role);
                    System.out.println(" UserService \n email: "+email + "\nMK: "+password);
                    Intent outIntent = new Intent(USER_SERVICE_EVENT);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                default:{
                }
            }
        }
    }
}