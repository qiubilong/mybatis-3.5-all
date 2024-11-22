package com.experiment.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@MapperScan("com.experiment.mapper")
public class MyBatisConfig {

	 /* 创建 DefaultSqlSessionFactory --> mybatis全局配置 Configuration
	 *
	 *  实例化Mapper接口对应FactoryBean对象MapperFactoryBean -->注入SqlSessionFactory --> 实例化SqlSessionTemplate(ThreadLocal实现线程安全) --> 创建MapperProxyr代理对象 -->
	 */

	@Bean
	public SqlSessionFactory createSqlSessionFactory() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sessionFactory;
	}
}