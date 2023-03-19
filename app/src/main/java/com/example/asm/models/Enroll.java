package com.example.asm.models;

public class Enroll {
    private Integer id,coursesId,userId;

    public Enroll(Integer id, Integer coursesId, Integer userId) {
        this.id = id;
        this.coursesId = coursesId;
        this.userId = userId;
    }

    public Enroll() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Integer coursesId) {
        this.coursesId = coursesId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
