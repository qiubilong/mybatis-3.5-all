package com.experiment.mapper;


import com.experiment.entity.User;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public interface UserMapper {

    User selectById(Integer id);

    void updateForName(String id,String username);
}
