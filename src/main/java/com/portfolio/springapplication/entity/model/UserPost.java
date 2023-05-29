package com.portfolio.springapplication.entity.model;

import lombok.Data;

@Data
public class UserPost {
    private int postId;
    private int locId;
    private String locName;
    private String address;
    private String category;
    private String content;
    private String evalScore;
    private String picDatas;
    private String createAt;
    private String updateAt;
    private Integer userLocFavId;
}
