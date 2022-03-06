package com.php25.qiuqiu.admin.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@Configuration
public class DbConfig {

    @Profile(value = {"local1"})
    @Primary
    @Bean
    public DataSource sqLiteDataSource() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:test.db");
        return sqLiteDataSource;
    }

    @Profile(value = {"local","dev", "test"})
    @Primary
    @Bean
    public DataSource hikariDataSource(DbProperties dbProperties) {
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

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Primary
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
