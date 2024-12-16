package com.experiment.mapper;


import com.experiment.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface UserMapper {

    User selectById(Integer id);

    List<User> selectList(User userxx);

    @Insert({"<script>insert into user " +
            "(name, age, email) " +
            "values " +
            "<foreach collection='list' item='vo' separator=','> " +
            "(#{vo.name}, #{vo.age}, #{vo.email})" +
            "</foreach></script>"})
    int batchInsert(@Param("list") List<User> list);


    @Insert("insert into user(name, age, email) values (#{vo.name}, #{vo.age}, #{vo.email}  ) ")
    boolean insert(@Param("vo") User user);

}
