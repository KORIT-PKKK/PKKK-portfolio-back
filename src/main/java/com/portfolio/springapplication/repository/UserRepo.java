package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface UserRepo {

    @Select("CALL get_user_post(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<UserPost> getUserPost(int userId);

    @Select("CALL get_user_outline(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<UserOutline> getUserOutline(int userId);

    @Select("SELECT name, introduce, image_url FROM user_dtl WHERE user_id = #{userId}")
    @Options(statementType = StatementType.PREPARED)
    List<UserInfo> getUserInfo(int userId);

    @Select("CALL sign_up(#{username}, #{password}, #{name})")
    @Options(statementType = StatementType.CALLABLE)
    String signUp(String username, String password, String name);

    @Select("CALL user_update(#{userId}, #{name}, #{introduce}, #{imageUrl})")
    @Options(statementType = StatementType.CALLABLE)
    String updateUserInfo(int userId, String name, String introduce, String imageUrl);

    @Select("UPDATE user_mst SET password = #{password} WHERE user_id = #{userId} AND username = #{username}")
    @Options(statementType = StatementType.PREPARED)
    Integer changePassword(int userId, String username, String password);

    @Select("SELECT ud.user_id AS userId, ud.name, ud.image_url AS imageUrl, us.create_at AS subDate " +
            "FROM user_sub us " +
            "LEFT JOIN user_dtl ud ON ud.user_id = us.sub_user_id " +
            "WHERE us.user_id = #{userId}")
    @Options(statementType = StatementType.PREPARED)
    List<SubModel> getSubTo(int userId);

    @Select("SELECT ud.user_id AS userId, ud.name, ud.image_url AS imageUrl, us.create_at AS subDate " +
            "FROM user_sub us " +
            "LEFT JOIN user_dtl ud ON ud.user_id = us.user_id " +
            "WHERE us.sub_user_id = #{userId}")
    @Options(statementType = StatementType.PREPARED)
    List<SubModel> getSubMe(int userId);

    @Select("INSERT INTO user_sub (user_id, sub_user_id) " +
            "VALUES (#{userId}, #{subUserId})")
    @Options(statementType = StatementType.PREPARED)
    String subUser(int userId, int subUserId);

    @Select("INSERT INTO user_loc_fav (user_id, loc_id) VALUES (#{userId}, #{locId})")
    @Options(statementType = StatementType.PREPARED)
    String addFavLoc(int userId, int locId);

    @Select("CALL get_loc_fav_list(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<Location> getLocFavList(int userId);

    @Select("INSERT INTO user_post_fav (user_id, post_id) VALUES (#{userId}, #{postId})")
    @Options(statementType = StatementType.PREPARED)
    String addFavPost(int userId, int postId);

    @Select("CALL get_post_fav_list(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostOutline> getPostFavList(int userId);

    @Select("DELETE FROM user_sub " +
            "WHERE user_sub_id = #{userSubId}")
    @Options(statementType = StatementType.PREPARED)
    String unSub(int userSubId);

    @Select("DELETE FROM user_post_fav " +
            "WHERE user_post_fav_id = #{favId}")
    @Options(statementType = StatementType.PREPARED)
    String unFavPost(int favId);

    @Select("DELETE FROM user_loc_fav " +
            "WHERE user_loc_fav_id = #{favId}")
    @Options(statementType = StatementType.PREPARED)
    String unFavLoc(int favId);
}
