package com.portfolio.springapplication.entity.model;

import lombok.Data;

import java.util.Date;

@Data
public class PostOutline {
    private int postId;
    private int userId;
    private int locId;
    private String name;
    private String imageUrl;
    private String locName;
    private String address;
    private String category;
    private int picPostCnt;
    private String content;
    private String evalScore;
    private String picDatas;
    private String createAt;
    private String updateAt;
    private Integer userLocFavId;
    private Integer userPostFavId;
    private Integer userSubId;
}
