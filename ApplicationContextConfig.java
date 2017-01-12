package com.niit.Eshopback.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import com.niit.Eshopback.dao.CategoryDAO;
import com.niit.Eshopback.dao.CategoryDAOImpl;
import com.niit.Eshopback.dao.ProductDAO;
import com.niit.Eshopback.dao.ProductDAOImpl;
import com.niit.Eshopback.dao.SupplierDAO;
import com.niit.Eshopback.dao.SupplierDAOImpl;
import com.niit.Eshopback.dao.UserDAO;
import com.niit.Eshopback.dao.UserDAOImpl;
import com.niit.Eshopback.model.Category;
import com.niit.Eshopback.model.Product;
import com.niit.Eshopback.model.Supplier;
import com.niit.Eshopback.model.User;


@Configuration
@ComponentScan("com.niit.Eshopback")
@EnableTransactionManagement
public class ApplicationContextConfig {
	

    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName("org.h2.Driver");
    	dataSource.setUrl("jdbc:h2:tcp://localhost/~/smruti");
    	dataSource.setUsername("sa");
    	dataSource.setPassword("");
    	
    	return dataSource;
    }
    
    
    private Properties getHibernateProperties() {
    	Properties properties = new Properties();
    	properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	properties.put("hibernate.hbm2ddl.auto", "create");
    	return properties;
    }
    
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
    	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    	sessionBuilder.addProperties(getHibernateProperties());
    	sessionBuilder.addAnnotatedClasses(Category.class);
    	sessionBuilder.addAnnotatedClasses(Supplier.class);
    	sessionBuilder.addAnnotatedClasses(User.class);
    	sessionBuilder.addAnnotatedClasses(Product.class);
    	return sessionBuilder.buildSessionFactory();
    }
    
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}
    
    @Autowired
    @Bean(name = "categoryDAO")
    public CategoryDAO getCategoryDao(SessionFactory sessionFactory) {
    	return new CategoryDAOImpl(sessionFactory);
    }

    @Autowired
    @Bean(name = "ProductDAO")
    public ProductDAO getProductDao(SessionFactory sessionFactory) {
    	return new ProductDAOImpl(sessionFactory);
    }

    @Autowired
    @Bean(name = "SupplierDAO")
    public SupplierDAO getSupplierDao(SessionFactory sessionFactory) {
    	return new SupplierDAOImpl(sessionFactory);
    }



    @Autowired
    @Bean(name = "UserDAO")
    public UserDAO getUserDao(SessionFactory sessionFactory) {
    	return new UserDAOImpl(sessionFactory);
    }

}
