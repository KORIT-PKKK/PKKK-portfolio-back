package com.portfolio.springapplication.entity.model;

import lombok.Data;

@Data
public class Location {
    private int locId;
    private String locName;
    private String address;
    private int category;
    private double lat;
    private double lng;
    private Integer userLocFavId;
    private Double evalScore;
}
