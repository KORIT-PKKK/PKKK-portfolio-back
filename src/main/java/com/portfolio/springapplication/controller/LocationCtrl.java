package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loc")
public class LocationCtrl {

    @Autowired
    private LocationRepo locationRepo;
    @GetMapping("/list")
    public ResponseEntity<?> getLocationDatas(@RequestParam(value = "userId", required = false) Integer userId){
        return ResponseEntity.ok().body(locationRepo.getLocDatas(userId));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getLocation(@RequestParam("locId") int locId, @RequestParam(value = "userId", required = false) Integer userId){
        return ResponseEntity.ok().body(locationRepo.getLocation(locId, userId));
    }
}
