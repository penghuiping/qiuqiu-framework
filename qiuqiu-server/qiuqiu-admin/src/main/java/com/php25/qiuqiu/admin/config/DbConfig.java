package com.php25.qiuqiu.admin.config;

import com.php25.common.db.DbType;
import com.php25.common.db.EntitiesScan;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@Configuration
public class DbConfig {


    @Bean
    public DataSource druidDataSource(DbProperties dbProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(dbProperties.getDriverClassName());
        hikariDataSource.setJdbcUrl(dbProperties.getUrl());
        hikariDataSource.setUsername(dbProperties.getUsername());
        hikariDataSource.setPassword(dbProperties.getPassword());
        hikariDataSource.setAutoCommit(true);
        hikariDataSource.setConnectionTimeout(dbProperties.getConnectionTimeout());
        hikariDataSource.setIdleTimeout(dbProperties.getIdleTimeout());
        hikariDataSource.setMinimumIdle(dbProperties.getMinIdle());
        hikariDataSource.setMaxLifetime(dbProperties.getMaxLifetime());
        hikariDataSource.setMaximumPoolSize(dbProperties.getMaximumPoolSize());
        hikariDataSource.setPoolName(dbProperties.getPoolName());
        return hikariDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DbType dbType() {
        return DbType.MYSQL;
    }

    @PostConstruct
    public void init() {
        new EntitiesScan().scanPackage("com.php25.wx.user.repository.model");
    }
}
