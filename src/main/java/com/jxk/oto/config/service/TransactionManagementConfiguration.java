package com.jxk.oto.config.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
/**
 * @author 17122
 *
 */
@Configuration
//首先使用注解@EnableTransactionManagement开启事务支持后
//在service 方法加上注解@Transaction使用
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {
     @Autowired
     //注入DataSourceConfiguration里面的DataSource 通过createDataSource()获取
     private DataSource dataSource;
	/**
	 *关于事务的管理，需要返回PlatformTransactionManager的实现
	 */
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		// TODO Auto-generated method stub
		return new DataSourceTransactionManager(dataSource);
	}

}
