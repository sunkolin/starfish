package com.starfish.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Deprecated
@Configuration
@MapperScan(basePackages = {"com.starfish.**.dao"})
public class MybatisConfiguration {

    private static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Autowired
    @Qualifier("defaultProperties")
    private Properties defaultProperties;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setTypeAliasesPackage(defaultProperties.getProperty("mybatis_typeAliasesPackage"));
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(defaultProperties.getProperty("mybatis_mapperLocations")));
            sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(defaultProperties.getProperty("mybatis_configLocation")));
            return sessionFactory.getObject();
        } catch (Exception e) {
            logger.error("could not configure mybatis session factory");
            return null;
        }
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager", initMethod = "afterPropertiesSet")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
