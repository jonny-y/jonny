package com.jonny.mybatisplus.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//@TableName("user")  在实体和表名不一致时指定表名，
public class User {
    //@TableId  指定主键，如果实体变量名于数据库主键字段名不一致，需要此注解指定为主键
    private Long id;
    //@TableField("name")  指定对应数据库中的哪一字段，用于实体中属性名与字段名不一致时使用
    private String name;
    //@TableField(condition = SqlCondition.LIKE)  //使用实体作为查询条件时，使用这个注解可以修改默认的等号条件关系
    private Integer age;
    private String email;
    //直属上级
    private Long manager_id;
    private LocalDateTime create_time;

}
