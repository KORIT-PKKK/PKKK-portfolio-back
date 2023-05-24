package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.user.PwChangeReqDto;
import com.portfolio.springapplication.dto.user.UpdateReqDto;
import com.portfolio.springapplication.repository.UserRepo;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.exception.CustomException;
import com.portfolio.springapplication.security.exception.ErrorMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserCtrl {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserOutline(@RequestParam("userId") int userId){
        return ResponseEntity.ok().body(userRepo.getUserOutline(userId));
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserInfo(@RequestParam("userId") int userId){
        return ResponseEntity.ok().body(userRepo.getUserInfo(userId));
    }

    @PostMapping("/detail/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody UpdateReqDto updateReqDto){
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(updateReqDto.getUsername());

        return ResponseEntity.ok().body(userRepo.updateUserInfo(userPrincipalDetail.user().getId(), updateReqDto.getName(), updateReqDto.getIntroduce(), updateReqDto.getImageUrl()));
    }

    @PostMapping("/password/change")
    public ResponseEntity<?> changePassword(@RequestBody PwChangeReqDto pwChangeReqDto){
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail)  userPrincipalDetailService.loadUserByUsername(pwChangeReqDto.getUsername());

        System.out.println(userPrincipalDetail);

        String oldPassword = pwChangeReqDto.getOldPassword();
        String dbPassword = userPrincipalDetail.getPassword();
        String newPassword = pwChangeReqDto.getNewPassword();

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(oldPassword, dbPassword)){
            throw new CustomException("Invalid password", ErrorMap.builder()
                    .put("password", "Password do not match").build());
        }

        String cryptPassword = encoder.encode(newPassword);

        return ResponseEntity.ok().body(userRepo.changePassword(userPrincipalDetail.user().getId(), pwChangeReqDto.getUsername(), cryptPassword));
    }

    @GetMapping("/subto")
    public ResponseEntity<?> getSubTo(@RequestParam("userId") int userId){
        return ResponseEntity.ok().body(userRepo.getSubTo(userId));
    }

    @GetMapping("subme")
    public ResponseEntity<?> getSubMe(@RequestParam("userId") int userId){
        return ResponseEntity.ok().body(userRepo.getSubMe(userId));
    }
}
