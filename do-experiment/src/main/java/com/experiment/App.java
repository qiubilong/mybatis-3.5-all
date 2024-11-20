package com.experiment;

import com.experiment.entity.User;
import com.experiment.mapper.MomentCommentExtMapper;
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

        SqlSession session = sqlSessionFactory.openSession(); /* DefaultSqlSessionFactory.openSession()  */
        try {
            // 执行查询
            User user =  session.selectOne("com.experiment.mapper.UserMapper.selectById", 2233);

            // 创建动态代理
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
