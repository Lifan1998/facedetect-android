package com.example.facedetection.data.model;

import android.util.Pair;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class StudentsVO extends Student {
    public StudentsVO(int id, String studentNo, String name, int status, String avatar) {
        super(id, studentNo, name, status, avatar);
    }

    private List<Pair<Integer, Integer>> statusAndTotalMap;
    private Date formTime;
    private Date toTime;

}
