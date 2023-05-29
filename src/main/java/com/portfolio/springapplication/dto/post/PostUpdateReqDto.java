package com.portfolio.springapplication.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostUpdateReqDto {
    private String username;
    private Double evalScore;
    private List<String> picDatas;
    private String content;
}
