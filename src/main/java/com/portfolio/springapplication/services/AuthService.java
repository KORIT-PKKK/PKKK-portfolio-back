package com.portfolio.springapplication.services;

import com.portfolio.springapplication.config.RedisConfig;
import com.portfolio.springapplication.dto.ErrorResponseDto;
import com.portfolio.springapplication.dto.auth.*;
import com.portfolio.springapplication.repository.UserRepo;
import com.portfolio.springapplication.security.auth.UserPrincipalDetail;
import com.portfolio.springapplication.security.auth.UserPrincipalDetailService;
import com.portfolio.springapplication.security.exception.CustomException;
import com.portfolio.springapplication.security.exception.ErrorMap;
import com.portfolio.springapplication.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;


    public String signUp(SignUpReqDto signUpReqDto){
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String username = signUpReqDto.getUsername();
        String password = signUpReqDto.getPassword();
        String name = signUpReqDto.getName();

        String cryptPw = encoder.encode(password);



        return userRepo.signUp(username, cryptPw, name);
    }

    public SignInRespDto signIn(SignInReqDto signInReqDto) {
        String username = signInReqDto.getUsername();
        String password = signInReqDto.getPassword();

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(username);

        if (userPrincipalDetail == null) {
            throw new CustomException("Invalid user.", ErrorMap.builder()
                    .put("user", "User not exist.").build());
        }

        String dbPassword = userPrincipalDetail.getPassword();

        if (!encoder.matches(password, dbPassword)) {
            throw new CustomException("Invalid password", ErrorMap.builder()
                    .put("password", "Password not match.").build());
        }

        return jwtTokenProvider.generateToken(userPrincipalDetail);
    }

    public String signOut(SignOutReqDto signOutReqDto){
        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();
        String currentRtk = (String) redisTemplate.opsForValue().get(signOutReqDto.getUsername());
        String result = null;

        if (!Objects.equals(currentRtk, signOutReqDto.getRefreshToken())){
            throw new CustomException("Bad Request", ErrorMap.builder().put("refreshToken", "Bad Request.").build());
        }

        if (Boolean.TRUE.equals(redisTemplate.delete(signOutReqDto.getUsername()))){
            result = "User has signed out";
        };

        return result;
    }

    public RtkRespDto refreshToken(RtkReqDto rtkReqDto){
        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();

        String rtk = rtkReqDto.getRefreshToken();
        String storedRtk = (String) redisTemplate.opsForValue().get(rtkReqDto.getUsername());

        if (storedRtk == null || !storedRtk.equals(rtk)){
            // refresh token has expired
            throw new CustomException("RefreshToken does not exist.",
                    ErrorMap.builder()
                            .put("expired", "RefreshToken does not found. Please sign-in again").build());
        }

        UserPrincipalDetail userPrincipalDetail = (UserPrincipalDetail) userPrincipalDetailService.loadUserByUsername(rtkReqDto.getUsername());

        return jwtTokenProvider.reissueToken(userPrincipalDetail);
    }
}
