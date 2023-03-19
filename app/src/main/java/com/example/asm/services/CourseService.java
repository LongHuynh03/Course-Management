package com.example.asm.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asm.DAO.CourseDAO;
import com.example.asm.models.Courses;

import java.util.ArrayList;
import java.util.List;

public class CourseService extends IntentService {

    public static final String COURSE_SERVICE_EVENT = "COURSE_SERVICE_EVENT";
    public static final String COURSE_SERVICE_ACTION_INSERT = "COURSE_SERVICE_ACTION_INSERT";
    public static final String COURSE_SERVICE_ACTION_UPDATE = "COURSE_SERVICE_ACTION_UPDATE";
    public static final String COURSE_SERVICE_ACTION_DELETE = "COURSE_SERVICE_ACTION_DELETE";
    public static final String COURSE_SERVICE_ACTION_GET = "COURSE_SERVICE_ACTION_GET";

    private CourseDAO courseDAO;

    public CourseService() {
        super("CourseService");
        courseDAO = new CourseDAO(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case COURSE_SERVICE_ACTION_INSERT: {
                    String name = intent.getStringExtra("name");
                    String code = intent.getStringExtra("code");
                    String room = intent.getStringExtra("room");
                    String time = intent.getStringExtra("time");
                    String day = intent.getStringExtra("day");
                    String lecturer = intent.getStringExtra("lecturer");
                    Integer available = 1;
                    Courses course = new Courses(-1, available, name, code, time, day, room, lecturer);
                    courseDAO.insert(course);
//                    Boolean result = courseDAO.insert(course);
//                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
//                    outIntent.putExtra("result", result); // ????
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_ACTION_UPDATE: {
                    Integer id = intent.getIntExtra("id",0);
                    String name = intent.getStringExtra("name");
                    String code = intent.getStringExtra("code");
                    String time = intent.getStringExtra("time");
                    String day = intent.getStringExtra("day");
                    String room = intent.getStringExtra("room");
                    String lecturer = intent.getStringExtra("lecturer");
                    Integer available = 1;
                    Courses course = new Courses(id, available, name, code, time, day, room,lecturer);
                    courseDAO.update(course);
//                    Boolean result = courseDAO.update(course);
//                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
//                    outIntent.putExtra("result", result); // ????
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_ACTION_DELETE: {
                    int id = intent.getIntExtra("id",0);
                    courseDAO.delete(id);
//                    Boolean result = courseDAO.delete(id);
//                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
//                    outIntent.putExtra("result", result); // ????
//                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_ACTION_GET: {
                    ArrayList<Courses> result = (ArrayList<Courses>) courseDAO.getAll();
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result", result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    System.out.println("List: "+result);
                    break;
                }
                default:
                    break;
            }
        }

    }
}