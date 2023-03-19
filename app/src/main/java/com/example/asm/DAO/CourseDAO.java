package com.example.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm.database.Database;
import com.example.asm.models.Courses;

import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Database database;

    public CourseDAO(Context context) {
        database = Database.getInstance(context);
    }

    //select
    public List<Courses> getAll(){
        List<Courses> list = new ArrayList<>();
        String sql = "SELECT ID,NAME, CODE, ROOM, TIME, DAY,LECTURER, AVAILABLE FROM COURSES";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _name = cursor.getString(1);
                    String _code = cursor.getString(2);
                    String _room = cursor.getString(3);
                    String _time = cursor.getString(4);
                    String _day = cursor.getString(5);
                    String _lecturer = cursor.getString(6);
                    Integer _available = cursor.getInt(7);
                    Courses appCourses = new Courses(_id,_available,_name,_code,_time,_day,_room,_lecturer);
                    list.add(appCourses);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            System.out.println("Lỗi GetAll CourseDAO: "+e);
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    public boolean insert (Courses courses){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();//bắt đầu tương tác database
        try{
            ContentValues values = new ContentValues();
            values.put("NAME", courses.getName());
            values.put("CODE",courses.getCode());
            values.put("ROOM",courses.getRoom());
            values.put("TIME",courses.getTime());
            values.put("DAY",courses.getDay());
            values.put("LECTURER",courses.getLecturer());
            values.put("AVAILABLE",courses.getAvailable());
            long rows = db.insertOrThrow("COURSES",null,values);
            db.setTransactionSuccessful();
            result = rows >=1;
            System.out.println("Thêm thành công: ");
        }catch (Exception e){
            System.out.println("Lỗi Insert CourseDAO: "+e);
        }finally {
            db.endTransaction();//kết thúc tương tác database
        }
        return result;
    }

    public boolean update(Courses appCourses){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();//bắt đầu tương tác database
        try{
            ContentValues values = new ContentValues();
            values.put("NAME", appCourses.getName());
            values.put("CODE",appCourses.getCode());
            values.put("ROOM",appCourses.getRoom());
            values.put("TIME",appCourses.getTime());
            values.put("DAY",appCourses.getDay());
            values.put("LECTURER",appCourses.getDay());
            values.put("AVAILABLE",appCourses.getAvailable());
            long rows = db.update("COURSES",values,"ID=?", new String[]{appCourses.getId().toString()});
            db.setTransactionSuccessful();
            result = rows >=1;
        }catch (Exception e){
            System.out.println("Lỗi Update CourseDAO: "+e);
        }finally {
            db.endTransaction();//kết thúc tương tác database
        }
        return result;
    }

    public boolean delete(Integer id){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();//bắt đầu tương tác database
        try{
            long rows = db.delete("COURSES","ID=?", new String[]{id.toString()});
            db.setTransactionSuccessful();
            System.out.println("Xóa thành công: ");
            result = rows >=1;
        }catch (Exception e){
            System.out.println("Lỗi Delete CourseDAO: "+e);
        }finally {
            db.endTransaction();//kết thúc tương tác database
        }
        return result;
    }

    public boolean registerCourse(Integer idU, Integer idC){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();//bắt đầu tương tác database
        try{
            ContentValues values = new ContentValues();
            values.put("USERID", idU);
            values.put("COURSEID",idC);
            long rows = db.insertOrThrow("ENROLS",null,values);
            db.setTransactionSuccessful();
            result = rows >=1;
            System.out.println("Thêm khóa học thành công: ");
        }catch (Exception e){
            System.out.println("Lỗi Insert CourseDAO: "+e);
        }finally {
            db.endTransaction();//kết thúc tương tác database
        }
        return result;
    }

    public boolean unRegisterCourse(Integer id){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();//bắt đầu tương tác database
        try{
            long rows = db.delete("ENROLS","ID=?", new String[]{id.toString()});
            db.setTransactionSuccessful();
            System.out.println("Xóa khóa học thành công: ");
            result = rows >=1;
        }catch (Exception e){
            System.out.println("Lỗi Delete CourseDAO: "+e);
        }finally {
            db.endTransaction();//kết thúc tương tác database
        }
        return result;
    }
}
