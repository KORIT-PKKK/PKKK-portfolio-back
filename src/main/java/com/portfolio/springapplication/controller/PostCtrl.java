package com.portfolio.springapplication.controller;

import com.portfolio.springapplication.dto.post.FavReqDto;
import com.portfolio.springapplication.dto.post.PostAddReqDto;
import com.portfolio.springapplication.repository.PostRepo;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostCtrl {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok().body(postRepo.getAllPosts());
    }

    @GetMapping("/view")
    public ResponseEntity<?> getPostDtl(@RequestParam("postId") int postId) {
        return ResponseEntity.ok().body(postRepo.getPostDetail(postId));
    }

    @GetMapping("/location")
    public ResponseEntity<?> getLocPost(@RequestParam("locId") int locId){
        return ResponseEntity.ok().body(postRepo.getLocPost(locId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody PostAddReqDto postAddReqDto) {
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(postAddReqDto.getUsername());
        String pics = String.join(", ", postAddReqDto.getPicDatas());

        int postId = postRepo.addPost(
                userPrincipalDetail.user().getId(),
                postAddReqDto.getContent(),
                postAddReqDto.getLocId(),
                postAddReqDto.getEvalScore(),
                pics);
        Map<String, Integer> response = new HashMap<>();
        response.put("postId", postId);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/fav/add/loc")
    public ResponseEntity<?> addFavLoc(@RequestBody FavReqDto favReqDto){
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(favReqDto.getUsername());
        return ResponseEntity.ok().body(postRepo.addFavLoc(userPrincipalDetail.user().getId(), favReqDto.getElementId()));
    }

    @PostMapping("/fav/add/post")
    public ResponseEntity<?> addFavPost(@RequestBody FavReqDto favReqDto){
        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(favReqDto.getUsername());
        return ResponseEntity.ok().body(postRepo.addFavPost(userPrincipalDetail.user().getId(), favReqDto.getElementId()));
    }
}