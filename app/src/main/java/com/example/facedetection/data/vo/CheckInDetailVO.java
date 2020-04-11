package com.example.facedetection.data.vo;

import lombok.Data;

import java.util.List;

/**
 * @author fan.li
 * @date 2020-04-04
 * @description
 */
@Data
public class CheckInDetailVO {
    String className;
    int id;
    int classId;
    List<StudentVO> studentVOList;

}