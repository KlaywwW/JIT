package com.starvincci.JIT.Config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages= {"com.starvincci.JIT.Mysql"},sqlSessionFactoryRef="mysqlSqlSessionFactory")
public class MysqlConfig {
	
	//指定使用的数据源
		@Bean(name="mysqlDataSource")
		@ConfigurationProperties(prefix="spring.test2.datasource")
		public DataSource readDataSource() {
			return new HikariDataSource();
		}
		
		
		@Bean(name="mysqlSqlSessionFactory")
		public SqlSessionFactory readSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		    bean.setDataSource(dataSource);
		   // ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		    //bean.setMapperLocations(resolver.getResources("classpath*:com/starvincci/barcodeprint/readmapper/*.xml"));
			return bean.getObject();
		}
		
	
		@Bean(name="mysqlSqlSessionTemplate")
		 public SqlSessionTemplate readSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
			SqlSessionTemplate template=new SqlSessionTemplate(sqlSessionFactory);
		    return template;
		}

}
