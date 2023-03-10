package com.increff.pos.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.NamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class DbConfig {

	public static final String PACKAGE_POJO = "com.increff.pos.pojo";
	
	@Value("${jdbc.driverClassName}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddl;
	@Value("${hibernate.physical_naming_strategy}")
	private String hibernatePhysicalNamingStrategy;

//	@Bean	//for naming strategy
//	public LocalSessionFactoryBean sessionFactory() {
//		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//		sessionFactory.setDataSource(getDataSource());
//		sessionFactory.setPackagesToScan("com.increff.pos");
//		sessionFactory.setHibernateProperties(hibernateProperties());
//		return sessionFactory;
//	}
//
//	private Properties hibernateProperties() {
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", hibernateDialect);
//		properties.put("hibernate.show_sql", hibernateShowSql);
//		properties.put("hibernate.ejb.naming_strategy", "com.increff.pos.spring.SnakeCaseNamingStrategy");
//		properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
//
//		return properties;
//	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
//		logger.info("jdbcDriver: " + jdbcDriver + ", jdbcUrl: " + jdbcUrl + ", jdbcUsername: " + jdbcUsername);
		BasicDataSource bean = new BasicDataSource();
		bean.setDriverClassName(jdbcDriver);
		bean.setUrl(jdbcUrl);
		bean.setUsername(jdbcUsername);
		bean.setPassword(jdbcPassword);
		bean.setInitialSize(2);
		bean.setDefaultAutoCommit(false);
		//bean.setMaxTotal(10);
		bean.setMinIdle(2);
		bean.setValidationQuery("Select 1");
		bean.setTestWhileIdle(true);
		bean.setTimeBetweenEvictionRunsMillis(10 * 60 * 100);
		return bean;
	}

	@Bean(name = "entityManagerFactory")
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		//logger.info("hibernateDialect: " + jdbcDriver + ", hibernateHbm2ddl: " + hibernateHbm2ddl);
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setPackagesToScan(PACKAGE_POJO);
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(jpaAdapter);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", hibernateDialect);
		jpaProperties.put("hibernate.show_sql", hibernateShowSql);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
		jpaProperties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
		jpaProperties.put("hibernate.physical_naming_strategy", hibernatePhysicalNamingStrategy);
		bean.setJpaProperties(jpaProperties);
		return bean;
	}
	
	@Bean(name = "transactionManager")
	@Autowired
	public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
		JpaTransactionManager bean = new JpaTransactionManager();
		bean.setEntityManagerFactory(emf.getObject());
		return bean;
	}
}
