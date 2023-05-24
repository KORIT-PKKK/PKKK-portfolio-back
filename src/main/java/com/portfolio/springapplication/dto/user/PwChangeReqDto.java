package com.portfolio.springapplication.dto.user;

import lombok.Data;

@Data
public class PwChangeReqDto {
    private String username;
    private String oldPassword;
    private String newPassword;
}
