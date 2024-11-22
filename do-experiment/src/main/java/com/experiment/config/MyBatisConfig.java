package com.experiment.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@Component
@Configuration
@MapperScan("com.experiment.mapper")
public class MyBatisConfig {

	 /* 创建 DefaultSqlSessionFactory --> mybatis全局配置 Configuration
	 *
	 *  实例化Mapper接口对应FactoryBean对象MapperFactoryBean -->注入SqlSessionFactory --> 实例化SqlSessionTemplate(ThreadLocal实现线程安全) --> 创建MapperProxyr代理对象 -->
	 */

	/*@Bean
	public SqlSessionFactory createSqlSessionFactory() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sessionFactory;
	}
*/
	/* 使用spring配置mybatis，创建DefaultSqlSessionFactory */
	@Bean
	public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com.experiment.mapper"));
		sqlSessionFactoryBean.setDataSource(createDataSource());/* 必须手动指定数据源 */
		return sqlSessionFactoryBean;
	}

	@Bean
	public DataSource createDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();/* 简单数据源，非动态数据源AbstractRoutingDataSource */
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/experiment?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}
}