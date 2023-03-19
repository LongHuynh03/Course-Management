package com.example.asm.models;

public class Courses {
    private Integer id,available;
    private String name,code,time,day,room,lecturer;

    public Courses() {
    }

    public Courses(Integer id, Integer available, String name, String code, String time, String day, String room, String lecturer) {
        this.id = id;
        this.available = available;
        this.name = name;
        this.code = code;
        this.time = time;
        this.day = day;
        this.room = room;
        this.lecturer = lecturer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
