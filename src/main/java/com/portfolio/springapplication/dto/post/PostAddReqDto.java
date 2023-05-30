package com.portfolio.springapplication.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostAddReqDto {
    private String username;
    private String content;
    private int locId;
    private Double evalScore;
    private List<String> picDatas;
}
