package com.portfolio.springapplication.entity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
public class PostDetail {
    private int postId;
    private int userId;
    private int locId;
    private String name;
    private String imageUrl;
    private String locName;
    private String address;
    private String category;
    private int postCount;
    private int picCount;
    private int flwCount;
    private String content;
    private int postViewCnt;
    private String evalScore;
    private String picDatas;
    private String createAt;
    private String updateAt;
    private Integer userLocFavId;
    private Integer userPostFavId;
    private Integer userSubId;
}
