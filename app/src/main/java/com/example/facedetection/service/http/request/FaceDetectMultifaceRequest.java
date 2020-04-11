package com.example.facedetection.service.http.request;


import lombok.Data;

/**
 * @author fan.li
 * @date 2020-04-04
 * @description
 */
@Data
public class FaceDetectMultifaceRequest {
    String image;
    Integer userId;
    Integer classroomId;
    Integer checkInId;
    boolean isCreateCheckIn;
}
