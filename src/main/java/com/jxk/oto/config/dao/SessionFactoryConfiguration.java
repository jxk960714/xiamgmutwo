package com.jxk.oto.config.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author 17122
 *
 */
@Configuration
public class SessionFactoryConfiguration {
	private static String mybatisConfigFile;
	@Value("${mybatis_config_file}")
	public void setMybatisConfigFile(String mybatisConfigFile) {
		SessionFactoryConfiguration.mybatisConfigFile = mybatisConfigFile;
	}
	private static String mapperPath;
	@Value("${mapper_path}")
	public void setMapperPath(String mapperPath) {
		SessionFactoryConfiguration.mapperPath = mapperPath;
	}
	@Value("${type_alias_package}")
	private  String typeAliasesPackage="entity";
	@Autowired
	private DataSource dataSource;
	
	
	
	


	



	/**创建sqlSessionFactoryBean 实例，利用设置 Mapper 映射路径
	 * 设置datasource数据源
	 * @return
	 * @throws IOException 
	 */
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactoryBean  createsqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		//设置mybatis-configuration 扫描路径
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFile));
		//添加Mapper扫描路径
		PathMatchingResourcePatternResolver  pathMatchingResourcePatternResolver=new PathMatchingResourcePatternResolver();
		String packageSearchPath=ResourcePatternResolver.CLASSPATH_URL_PREFIX+mapperPath;
		sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSearchPath));
		//设置dataSource
		sqlSessionFactoryBean.setDataSource(dataSource);
		//设置typeAlias包扫描路径
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		return sqlSessionFactoryBean;
		
	}

}
