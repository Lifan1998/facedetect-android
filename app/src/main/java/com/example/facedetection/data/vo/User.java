package com.example.facedetection.data.vo;


import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-04-09 01:11:20
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -16112931440245836L;

    private Integer id;

    private String username;

    private String password;

    private String moblie;

    private Date addTime;

    private Date updateTime;

    private String email;

    private String nickname;

    private String avatar;
    /**
     * 用户类型
     */
    private Integer type;
    /**
     * 身份证号
     */
    private String idcard;


}
