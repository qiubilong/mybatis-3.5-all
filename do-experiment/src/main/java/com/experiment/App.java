package com.experiment;

import com.experiment.entity.User;
import com.experiment.mapper.MomentCommentExtMapper;
import com.experiment.mapper.UserMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/11/12
 */
@Slf4j
public class App {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);


        testBatchInsertInSession(sqlSessionFactory);

        /* DefaultSqlSession = DefaultSqlSessionFactory.openSession(),线程不安全  */
        SqlSession session = sqlSessionFactory.openSession();
        try {
            // xml - 简单参数
            User user =  session.selectOne("com.experiment.mapper.UserMapper.selectById", 2233);

            // xml - 对象参数
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User userParam = new User();
            userParam.setName("value%");
            List<User> users =  userMapper.selectList(userParam);//相当于执行 session.selectList

            // sql注解 - 多个简单参数 -- ParamMap
            MomentCommentExtMapper commentExtMapper = session.getMapper(MomentCommentExtMapper.class);
            Boolean updateCommentReplyNum = commentExtMapper.updateCommentReplyNum(121L,3);
            System.out.println(commentExtMapper.getClass());

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
    }


    public static void testBatchInsertInSession( SqlSessionFactory sqlSessionFactory){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);

        List<User> list = generateUserList();
        List<List<User>> lists = Lists.partition(list, 5);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        long start = System.currentTimeMillis();
        for (List<User> users : lists) {
            for (User user : users) {
                mapper.insert(user);
            }
            sqlSession.commit();
        }
        log.info("testBatchInsertInSession cost={}",( System.currentTimeMillis() - start));
        sqlSession.close();
    }

    public static List<User> generateUserList() {
        Date now = new Date();
        int max = 100;
        List<User> list = new ArrayList<>(max);
        for (int i = 1; i < max; i++) {
            User user = new User();
            user.setAge(i%30+ 3000);
            user.setName("name"+i);
            user.setEmail((i+1000000)+"@qq.com");
            if(i%2 ==0){
                user.setEmail((i+3000000)+"@qq.com");
            }
            user.setCreateTime(now);
            user.setUpdateTime(now);
            list.add(user);
        }
        return list;
    }
}
