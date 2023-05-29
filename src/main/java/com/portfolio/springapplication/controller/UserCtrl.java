package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.post.FavReqDto;
import com.portfolio.springapplication.dto.user.PwChangeReqDto;
import com.portfolio.springapplication.dto.user.SubReqDto;
import com.portfolio.springapplication.dto.user.UndoReqDto;
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

    @PostMapping("/subscribe/add")
    public ResponseEntity<?> addSub(@RequestBody SubReqDto subReqDto){
        return ResponseEntity.ok().body(userRepo.subUser(subReqDto.getUserId(), subReqDto.getSubUserId()));
    }

    @GetMapping("/subscribe/subTo")
    public ResponseEntity<?> getSubTo(@RequestParam("userId") int userId){
        return ResponseEntity.ok().body(userRepo.getSubTo(userId));
    }

    @GetMapping("/subscribe/subMe")
    public ResponseEntity<?> getSubMe(@RequestParam("userId") int userId){
        return ResponseEntity.ok().body(userRepo.getSubMe(userId));
    }

    @DeleteMapping("/subscribe/unSub")
    public ResponseEntity<?> unSub(@RequestBody UndoReqDto undoReqDto){
        return ResponseEntity.ok().body(userRepo.unSub(undoReqDto.getElementId()));
    }

    @PostMapping("/favorite/loc/add")
    public ResponseEntity<?> addFavLoc(@RequestBody FavReqDto favReqDto){
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(favReqDto.getUsername());
        return ResponseEntity.ok().body(userRepo.addFavLoc(userPrincipalDetail.user().getId(), favReqDto.getElementId()));
    }

    @PostMapping("/favorite/post/add")
    public ResponseEntity<?> addFavPost(@RequestBody FavReqDto favReqDto){
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(favReqDto.getUsername());
        return ResponseEntity.ok().body(userRepo.addFavPost(userPrincipalDetail.user().getId(), favReqDto.getElementId()));
    }

    @DeleteMapping("/favorite/loc/undo")
    public ResponseEntity<?> undoFavLoc(@RequestBody UndoReqDto undoReqDto){
        return ResponseEntity.ok().body(userRepo.unFavLoc(undoReqDto.getElementId()));
    }

    @DeleteMapping("/favorite/post/undo")
    public ResponseEntity<?> undoFavPost(@RequestBody UndoReqDto undoReqDto){
        return ResponseEntity.ok().body(userRepo.unFavPost(undoReqDto.getElementId()));
    }
}
