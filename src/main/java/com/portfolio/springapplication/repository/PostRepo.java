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
    @Select("CALL get_post_dtl(#{postId}, #{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostDetail> getPostDetail(Integer postId, Integer userId);

    @Select("CALL get_post_overview(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostOutline> getAllPosts(Integer userId);

    @Select("CALL add_post(#{userId}, #{content}, #{locId}, #{evalScore}, #{picDatas})")
    @Options(statementType = StatementType.CALLABLE)
    int addPost(int userId, String content, int locId, double evalScore, String picDatas);

    @Select("CALL post_update(#{userId}, #{evalScore}, #{picDatas}, #{content})")
    @Options(statementType = StatementType.CALLABLE)
    String updatePost(int userId, Double evalScore, String picDatas, String content);

    @Select("CALL get_location_post_overview(#{locId}, #{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostOutline> getLocPost(Integer locId, Integer userId);

    @Select("CALL get_user_post_overview(#{postUserId}, #{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<PostOutline> getUserPost(Integer postUserId, Integer userId);

    @Select("CALL delete_post(#{postId}, #{userId})")
    @Options(statementType = StatementType.CALLABLE)
    String deletePost(int postId, int userId);
}
