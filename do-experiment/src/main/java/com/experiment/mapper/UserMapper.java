package com.experiment.mapper;


import com.experiment.entity.User;

import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface UserMapper {

    User selectById(Integer id);

    List<User> selectList(User userxx);
}
