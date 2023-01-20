package com.php25.qiuqiu.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@Slf4j
@Configuration
public class DbConfig {

    @Profile(value = {"local"})
    @Bean
    public DataSource sqLiteDataSource() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:test.db");
        return sqLiteDataSource;
    }

    @Profile(value = {"dev","docker"})
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
        hikariDataSource.setConnectionTestQuery("select 'x' from dual");
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
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer
                .setBasePackage("com.php25.qiuqiu.user.dao," +
                                "com.php25.qiuqiu.job.dao," +
                                "com.php25.qiuqiu.monitor.dao," );
//                                "com.php25.common.timer.dao");
        return mapperScannerConfigurer;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**/*.xml"));
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        mybatisSqlSessionFactoryBean.setPlugins(interceptor);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(new SqlInjectorPlus());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
        return mybatisSqlSessionFactoryBean.getObject();
    }
}

class SqlInjectorPlus extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        //继承原有方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        //注入新方法
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}
