package com.example.facedetection.data.emus;

public enum StudentStatus {
    NORMAL(1, "正常"),
    BE_LATE(2, "迟到"),
    ABSENCE(3, "缺勤"),
    LEAVE(4, "事假");
    int value;
    String desc;


    StudentStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc () { return  desc; }

    public static StudentStatus fromValue(int value) {
        for (StudentStatus studentStatus : StudentStatus.values()) {
            if (studentStatus.getValue() == value) {
                return studentStatus;
            }
        }
        return NORMAL;
    }
}
