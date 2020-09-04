package com.jonny.mybatisplus.mybatisplus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jonny.mybatisplus.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义SQL查询
     */
    //@Select("select * from user ${ew.customSqlSegment}")
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);
 }
