//package com.sunny.configuration;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.util.Properties;
//
///**
// * 数据源配置
// *
// * @author sunny
// * @version 1.0.0
// * @since 2015-08-03
// */
//@Configuration
//@EnableTransactionManagement
//public class DataSourceConfiguration {
//
//    @Autowired
//    @Qualifier("defaultProperties")
//    private Properties defaultProperties;
//
//    @Bean(name = "dataSource", destroyMethod = "close")
//    public BasicDataSource dataSource() {
//        String driverClassName = defaultProperties.getProperty("data_source_driver_class_name");
//        String url = defaultProperties.getProperty("data_source_url");
//        String username = defaultProperties.getProperty("data_source_username");
//        String password = defaultProperties.getProperty("data_source_password");
//        Boolean testOnBorrow = Boolean.valueOf(defaultProperties.getProperty("data_source_test_on_borrow"));
//        Boolean testWhileIdle = Boolean.valueOf(defaultProperties.getProperty("data_source_test_while_idle"));
//        Long timeBetweenEvictionRunsMillis = Long.valueOf(defaultProperties.getProperty("data_source_time_between_eviction_runs_millis"));
//
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setTestOnBorrow(testOnBorrow);
//        dataSource.setTestWhileIdle(testWhileIdle);
//        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//
//        return dataSource;
//    }
//
//}
