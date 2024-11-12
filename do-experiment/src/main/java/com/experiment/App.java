package com.experiment;

import com.experiment.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author chenxuegui
 * @since 2024/11/12
 */
public class App {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            // 执行查询 底层执行jdbc 3
            User user =  session.selectOne("com.experiment.mapper.UserMapper.selectById", 1);

            User user2 =  session.selectOne("com.experiment.mapper.UserMapper.selectById", 1);

            // 创建动态代理
           /* UserMapper mapper = session.getMapper(UserMapper.class);
            System.out.println(mapper.getClass());
            User user = mapper.selectById(1);*/
            System.out.println(user.getUserName());

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
    }

}
