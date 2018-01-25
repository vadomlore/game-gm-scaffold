package com.sygamer.starreduxgm.security.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "paymentDataSource")
    @Qualifier("paymentDataSource")
    @ConfigurationProperties(prefix="spring.payment.datasource")
    public DataSource paymentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "gmDataSource")
    @Qualifier("gmDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.gm.datasource")
    public DataSource gmDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "gmJdbcTemplate")
    public JdbcTemplate gmJdbcTemplate(
            @Qualifier("gmDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "paymentJdbcTemplate")
    public JdbcTemplate paymentJdbcTemplate(
            @Qualifier("paymentDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
