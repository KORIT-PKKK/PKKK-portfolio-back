package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.model.SubModel;
import com.portfolio.springapplication.entity.model.UserInfo;
import com.portfolio.springapplication.entity.model.UserOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface UserRepo {

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

    @Select("SELECT ud.user_id AS userId, ud.name, ud.image_url AS imageUrl, us.create_at AS subDate" +
            "FROM user_sub us" +
            "LEFT JOIN user_dtl ud ON ud.user_id = us.sub_user_id" +
            "WHERE us.user_id = #{userId}")
    @Options(statementType = StatementType.PREPARED)
    List<SubModel> getSubTo(int userId);

    @Select("SELECT ud.user_id AS userId, ud.name, ud.image_url AS imageUrl, us.create_at AS subDate" +
            "FROM user_sub us" +
            "LEFT JOIN user_dtl ud ON ud.user_id = us.user_id" +
            "WHERE us.sub_user_id = #{userId}")
    @Options(statementType = StatementType.PREPARED)
    List<SubModel> getSubMe(int userId);
}