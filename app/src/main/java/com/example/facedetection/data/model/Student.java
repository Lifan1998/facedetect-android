package com.example.facedetection.data.model;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Student {
    private int id;
    private String studentNo;

    public Student(int id, String studentNo, String name, int status, String avatar) {
        this.id = id;
        this.studentNo = studentNo;
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }

    private String name;
    private int status;
    private String avatar;


    public Student(int id, String name, int status, String avatar) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }
}
