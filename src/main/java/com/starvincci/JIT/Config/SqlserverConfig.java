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
@MapperScan(basePackages= {"com.starvincci.JIT.Sqlserver"},sqlSessionFactoryRef="sqlserverSqlSessionFactory")
public class SqlserverConfig {
	//指定使用的数据源
		@Bean(name="sqlserverDataSource")
		@Primary
		@ConfigurationProperties(prefix="spring.test1.datasource")
		public DataSource sqlserverDataSource() {
			return new HikariDataSource();
		}
		
		@Primary
		@Bean(name="sqlserverSqlSessionFactory")
		public SqlSessionFactory sqlserverSqlSessionFactory(@Qualifier("sqlserverDataSource") DataSource dataSource)  {
		
		   // ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		    //bean.setMapperLocations(resolver.getResources("classpath*:com/starvincci/barcodeprint/readmapper/*.xml"));
			try {
				SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			    bean.setDataSource(dataSource);
				return bean.getObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("NG");
				return null;
			}
		}
		
		@Primary
		@Bean(name="sqlserverSqlSessionTemplate")
		 public SqlSessionTemplate sqlserverSqlSessionTemplate(@Qualifier("sqlserverSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
			SqlSessionTemplate template=new SqlSessionTemplate(sqlSessionFactory);
		    return template;
		}
}
