package com.experiment.service;

import com.experiment.entity.User;
import com.experiment.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenxuegui
 * @since 2024/11/21
 */
@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
}
