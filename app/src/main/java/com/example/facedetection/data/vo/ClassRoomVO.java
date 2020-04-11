package com.example.facedetection.data.vo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author fan.li
 * @date 2020-04-03
 * @description
 */
@Data
public class ClassRoomVO {
    String id;
    String name;
    Date fromTime;
    Date toTime;
    List<StudentCountVO> studentDTOS;
}
