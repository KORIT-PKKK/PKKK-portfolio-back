package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.model.PostDetail;
import com.portfolio.springapplication.entity.model.PostOutline;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface PostRepo {
    @Select("CALL get_post_dtl(#{postId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostDetail> getPostDetail(Integer postId);

    @Select("CALL get_post_overview")
    @Options(statementType = StatementType.CALLABLE)
    List<PostOutline> getAllPosts();

    @Select("CALL add_post(#{userId}, #{content}, #{locId}, #{evalScore}, #{picDatas})")
    @Options(statementType = StatementType.CALLABLE)
    int addPost(int userId, String content, int locId, int evalScore, String picDatas);

    @Select("CALL get_location_post_overview(#{locId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostOutline> getLocPost(int locId);

    @Select("INSERT INTO user_loc_fav (user_id, loc_id) VALUES (#{userId}, #{locId})")
    @Options(statementType = StatementType.PREPARED)
    String addFavLoc(int userId, int locId);

    @Select("INSERT INTO user_post_fav (user_id, post_id) VALUES (#{userId}, #{postId})")
    @Options(statementType = StatementType.PREPARED)
    String addFavPost(int userId, int postId);
}
