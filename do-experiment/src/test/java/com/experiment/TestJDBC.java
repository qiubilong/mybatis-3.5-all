package com.experiment;

import com.experiment.entity.User;
import org.junit.Test;

import java.sql.*;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class TestJDBC {

    @Test
    public  void test() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            // 1.加载驱动,非必须
            //Class.forName("com.mysql.jdbc.Driver");

            // 2.创建连接
            conn= DriverManager.   /* 如果没有 Class.forName("com.mysql.jdbc.Driver")， 这里会通过SPI机制加载Driver */
                    getConnection("jdbc:mysql://localhost:3306/experiment?characterEncoding=utf8&useSSL=false", "root", "123456");
                    //getConnection("jdbc:mysql://localhost:3306/experiment?characterEncoding=utf8&useSSL=false&useServerPrepStmts=true&cachePrepStmts=true", "root", "123456");

            // 开启事务
            conn.setAutoCommit(false);

            // SQL语句  参数#{}  ${}  <if>
            String sql="select id,user_name,create_time from  t_user where user_name= ? ;";

            /*开启预处理时，首先向数据库执行预处理，如 Prepare	select id,name,create_time from  t_user where name= ? */
            //可以在C:\ProgramData\MySQL\MySQL Server 5.7\Data\DESKTOP-SKE8KR8.log 数据库通用日志中验证
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"xxx");/*这里对字符转义处理，防止sql注入*/

            /*开启预处理时，这里只是传输参数。因此如果开启了SQL预编译，又不开启缓存，那么执行SQL需要两次io交互 */
            pstmt.execute();
            /* 开启sql预编译与否性能测试
             * https://blog.csdn.net/weixin_36380516/article/details/124958213
             */


            //处理结果集
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {//处理一行数据
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setCreateTime(rs.getDate("create_time"));
                System.out.println(user.toString());
            }


            /* useServerPrepStmts=true&cachePrepStmts=true --> 开启预处理--> 缓存 */
            if(pstmt!=null){
                pstmt.close();
            }

            /* useServerPrepStmts=true&cachePrepStmts=true --> 开启预处理 --> 从缓存中获取*/
            PreparedStatement pstmt1 = conn.prepareStatement(sql);
            pstmt1.setString(1,"xxx2");
            //开启预处理，只是传输参数
            pstmt1.execute();

            //返回主键测试
            testGenerateKey(conn);

            // 提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            conn.rollback();
        }
        finally{
            // 关闭资源
            try {
                if(pstmt!=null){
                    pstmt.close(); //  useServerPrepStmts=true&cachePrepStmts=true --> 缓存
                }

                if(conn!=null){
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void testGenerateKey(Connection conn) throws Exception{
        String sql = "INSERT INTO t_user (user_name) VALUES (?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);/* 有无Statement.RETURN_GENERATED_KEYS，sql插入时都会返回主键 */
        pstmt.setString(1, "value1");
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows > 0) {
            ResultSet rs = pstmt.getGeneratedKeys(); /* 需要设置Statement.RETURN_GENERATED_KEYS */
            if (rs.next()) {
                long generatedId = rs.getLong(1); // 获取生成的主键值
                System.out.println("Generated ID: " + generatedId);
            }
        }
    }

    @Test
    public void testII(){
        BaseDao baseDao = new BaseDao();
        // 3个查询条件  1   2   3
        User user = baseDao.executeJavaBean("select id,name,create_time from user where id=?", User.class, 1);

        System.out.println(user);
    }


    @Test
    public  void prepareTest() throws SQLException {
        Connection conn=null;
        try {
            // 1.加载驱动
            //Class.forName("com.mysql.jdbc.Driver");

            // 2.创建连接
            conn= DriverManager.   // SPI
                    getConnection("jdbc:mysql://localhost:3306/mybatis_example?useServerPrepStmts=true&cachePrepStmts=true", "root", "123456");

            // 开启事务
            conn.setAutoCommit(false);

            // SQL语句  参数#{}  ${}  <if>
            String sql="  select id,name,create_time from  t_user where id=?;";

            /* 获得sql执行者  ：
            * 1.预编译（需数据库支持,MySQl默认已关闭）
            *    1.1 &useServerPrepStmts=true  这样才能保证mysql驱动会先把SQL语句发送给服务器进行预编译，然后在执行executeQuery()时只是把参数发送给服务器。
            *    1.2 &cachePrepStmts=true
            * 2.防SQL注入（敏感字符转义）
            */
            try(PreparedStatement pstmt=conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);


                // 执行查询
                pstmt.execute();
                try(ResultSet rs = pstmt.getResultSet()) {
                    //ResultSet rs= pstmt.executeQuery();

                    rs.next();
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setCreateTime(rs.getDate("create_time"));
                    System.out.println(user.toString());
                }
            }

            try(PreparedStatement pstmt=conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);


                // 执行查询
                pstmt.execute();
                try(ResultSet rs = pstmt.getResultSet()) {
                    //ResultSet rs= pstmt.executeQuery();

                    rs.next();
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setCreateTime(rs.getDate("create_time"));
                    System.out.println(user.toString());
                }
            }

            // 提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            conn.rollback();
        }
        finally{
            // 关闭资源
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    @Test
    public  void batchTest() throws SQLException {
        Connection conn=null;
        try {
            // 1.加载驱动
            //Class.forName("com.mysql.jdbc.Driver");

            // 2.创建连接
            conn= DriverManager.   // SPI
                    getConnection("jdbc:mysql://localhost:3306/mybatis_example?useServerPrepStmts=true&cachePrepStmts=true", "root", "123456");

            // 开启事务
            conn.setAutoCommit(false);

            String sql = "INSERT INTO t_user(user_name) VALUES (?);";

            try(PreparedStatement pst=conn.prepareStatement(sql)) {

                for (int i = 0; i < 1000; i++) {
                    pst.setString(1, "xushu"+i);
                    pst.addBatch();
                }

                pst.executeBatch();
            }

            // 提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            conn.rollback();
        }
        finally{
            // 关闭资源
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
