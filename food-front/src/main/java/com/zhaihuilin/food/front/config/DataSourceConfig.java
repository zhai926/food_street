package com.zhaihuilin.food.front.config;//package com.zhaihuilin.com.zhaihui.food.front.config;//package com.hafu365.fresh.order.com.zhaihui.food.image.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//
///**
// * 线程池
// * Created by zhaihuilin on 2017/7/21  9:18.
// */
//@Configuration
//public class DataSourceConfig {
//
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.minIdle}")
//    private int minIdle;
//    @Value("${spring.datasource.maxAction}")
//    private int maxAction;
//    @Value("${spring.datasource.maxWaitMillis}")
//    private int maxWaitMillis;
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setMinIdle(minIdle);
//        dataSource.setMaxActive(maxAction);
//        dataSource.setMaxWait(maxWaitMillis);
//        return dataSource;
//    }
// }
//
