package com.example.facedetection.data.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author fan.li
 * @date 2020-04-04
 * @description
 */
@Data
public class CheckInItemVO {
    int id;
    String className;
    Date recentTime;
    /**
     * 总人数
     */
    int studentTotalNum;
    /**
     * 识别人数
     */
    int studentNum;
}
