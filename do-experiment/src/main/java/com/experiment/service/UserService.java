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

    @Autowired /* MapperProxy 对象，持有SqlSessionTemplate -->  SqlSessionInterceptor方法拦截 --> 每个线程创建独立的DefaultSqlSession实现线程安全 */
    private UserMapper userMapper;

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
}
