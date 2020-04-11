package com.example.facedetection.data.vo;


import lombok.Data;

/**
 * @author fan.li
 * @date 2020-04-03
 * @description
 */
@Data
public class StudentCountVO {

    int id;
    String name;
    /**
     * 学号
     */
    String studentNo;
    /**
     * 正常打卡次数
     */
    int normal;
    int beLate;
    int absence;
    int leave;
}
