package com.projectkubbank.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CommonBeans {
    @Value("${driver.class.name}")
    String driverClassName;
    @Value("${datasource.url}")
    String datasourceUrl;
    @Value("${datasource.username}")
    String datasourceUsername;
    @Value("${datasource.password}")
    String datasourcePassword;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(datasourceUrl);
        config.setUsername(datasourceUsername);
        config.setPassword(datasourcePassword);
        config.setDriverClassName(driverClassName);
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
