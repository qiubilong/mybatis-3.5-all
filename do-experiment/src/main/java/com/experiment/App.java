package com.experiment;

import com.experiment.entity.User;
import com.experiment.mapper.MomentCommentExtMapper;
import com.experiment.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/11/12
 */
public class App {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        SqlSession session = sqlSessionFactory.openSession(); /* DefaultSqlSessionFactory.openSession()  */
        try {
            // xml - 简单参数
            User user =  session.selectOne("com.experiment.mapper.UserMapper.selectById", 2233);

            // xml - 对象参数
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User userParam = new User();
            userParam.setUserName("value%");
            List<User> users =  userMapper.selectList(userParam);//相当于执行 session.selectList

            // sql注解 - 多个简单参数
            MomentCommentExtMapper commentExtMapper = session.getMapper(MomentCommentExtMapper.class);
            System.out.println(commentExtMapper.getClass());
            Integer updateCommentReplyNum = commentExtMapper.updateCommentReplyNum(121L,3);

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
    }

}
