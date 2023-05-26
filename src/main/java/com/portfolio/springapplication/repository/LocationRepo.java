package com.portfolio.springapplication.repository;

import com.portfolio.springapplication.entity.model.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface LocationRepo {
    @Select("CALL get_location_datas(#{userId})")
    @Options(statementType = StatementType.CALLABLE)
    List<Location> getLocDatas(Integer userId);

    @Select("CALL get_location(#{locId}, #{userId})")
    @Options(statementType = StatementType.CALLABLE)
    Location getLocation(int locId, Integer userId);
}
