package com.example.asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    public Database(Context context) {
        super(context, "MyDatabase", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE IF NOT EXISTS USERS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "EMAIL TEXT," +
                "PASSWORD TEXT," +
                "NAME TEXT," +
                "ROLE INTEGER)";
        db.execSQL(sql1);
        String sql2 = "CREATE TABLE IF NOT EXISTS COURSES (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "CODE TEXT," +
                "ROOM TEXT," +
                "TIME TEXT," +
                "DAY DATE," +
                "LECTURER TEXT," +
                "AVAILABLE INTEGER)";
        db.execSQL(sql2);
        String sql3 = "CREATE TABLE IF NOT EXISTS ENROLS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERID INTEGER," +
                "COURSEID INTEGER," +
                "FOREIGN KEY(USERID) REFERENCES USERS(ID)," +
                " FOREIGN KEY(COURSEID) REFERENCES COURSES(ID))";
        db.execSQL(sql3);

        String sql4 = "insert into USERS (EMAIL, PASSWORD, NAME, ROLE) " +
                        "values ('1@gmail.com', '1', 'Bridget Gatch', 1);";
        db.execSQL(sql4);
        String sql5 = "insert into USERS (EMAIL, PASSWORD, NAME, ROLE) " +
                        "values ('2@gmail.com', '1', 'Derrick Dagleas', 2);";
        db.execSQL(sql5);

        String sql6 = "insert into COURSES (NAME, CODE, ROOM, TIME, DAY, LECTURER, AVAILABLE)" +
                        " values ('Android Nâng cao', 'MOB201', 33, '12:04 AM', '13-06-2022', 'Lambert Starr', 1);";
        db.execSQL(sql6);
        String sql7 = "insert into COURSES (NAME, CODE, ROOM, TIME, DAY, LECTURER, AVAILABLE) " +
                        "values ('Dự án mẫu', 'MOB1021', 19, '12:37 PM', '14-03-2022', 'Fletcher Street', 1);";
        db.execSQL(sql7);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ENROLLS");
            db.execSQL("DROP TABLE IF EXISTS USERS");
            db.execSQL("DROP TABLE IF EXISTS COURSES");
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
