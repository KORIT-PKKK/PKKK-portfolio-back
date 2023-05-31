package com.portfolio.springapplication.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostUpdateReqDto {
    private int postId;
    private int locId;
    private int postEvalId;
    private String username;
    private Double evalScore;
    private List<String> picDatas;
    private List<String> delPicDatas;
    private String content;
}
