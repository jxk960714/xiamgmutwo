package com.jxk.oto.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author 17122
 *
 */
@Configuration
//mybatis mapper 的扫描路径
@MapperScan("com.jxk.oto.dao")
public class DataSourceConfiguration {
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	/*
	 * 生成与spring-dao.xml对应的bean dataSource
	
	*/
	
	@Bean(name="dataSource")
	public  ComboPooledDataSource createDataSource() throws PropertyVetoException {
		//生成dataSource实例
		ComboPooledDataSource dataSource=new  ComboPooledDataSource();
		//跟配置文件一样设置一下信息
		//驱动
		dataSource.setDriverClass(jdbcDriver);
		//数据连接URL
		dataSource.setJdbcUrl(jdbcUrl);
		//用户名
		dataSource.setUser(jdbcUsername);
		//设置密码
		dataSource.setPassword(jdbcPassword);
		//配置c3p0连接池的私有属性
		//连接池最大线程数
		dataSource.setMaxPoolSize(30);
		//连接池最小线程数
		dataSource.setMinPoolSize(10);
		//关闭连接后不自动commit
		dataSource.setAutoCommitOnClose(false);
		//连接超时时间
		dataSource.setCheckoutTimeout(10000);
		//连接失败重试次数
		dataSource.setAcquireRetryAttempts(2);
		return dataSource;
		
	}
	
	
}
