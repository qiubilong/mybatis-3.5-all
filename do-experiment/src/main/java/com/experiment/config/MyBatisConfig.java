package com.experiment.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@Component
@Configuration
@MapperScan("com.experiment.mapper") /* 扫描Mapper接口-->注册Definition(name = xxxMapper,class =MapperFactoryBean) -->DefaultSqlSessionFactory.Configuration注册解析Mapper -->获取代理类MapperFactoryBean.getObject() -->Configuration.getMapper(xxx)  */
public class MyBatisConfig {

	 /*
	 *  实例化Mapper接口对应FactoryBean对象MapperFactoryBean -->注入SqlSessionFactory --> 实例化SqlSessionTemplate(ThreadLocal实现线程安全) --> 创建MapperProxy代理对象 -->
	 */

	/* 使用spring配置mybatis，创建DefaultSqlSessionFactory --> 创建mybatis全局配置 Configuration */
	@Bean
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(false);//关闭Mapper缓存--不需要CachingExecutor
		sqlSessionFactoryBean.setConfiguration(configuration);
		sqlSessionFactoryBean.setDataSource(createDataSource());/* 必须手动指定数据源 */
		return sqlSessionFactoryBean;
	}

	@Bean
	public DataSource createDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();/* 简单数据源 */
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/experiment?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}
}