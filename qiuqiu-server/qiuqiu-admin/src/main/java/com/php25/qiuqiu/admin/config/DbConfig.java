package com.php25.qiuqiu.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Lists;
import com.php25.common.core.dto.CurrentUser;
import com.php25.common.core.mess.SpringContextHolder;
import com.php25.common.db.FindCurrentUserStrategy;
import com.php25.common.db.SqlInjectorPlus;
import com.php25.common.db.UserDataPermissionHandler;
import com.php25.common.web.RequestUtil;
import com.php25.qiuqiu.user.service.GroupService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

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
        DataPermissionInterceptor dp = new DataPermissionInterceptor();
        dp.setDataPermissionHandler(new UserDataPermissionHandler(groupId -> {
            GroupService groupService = SpringContextHolder.getApplicationContext().getBean(GroupService.class);
            List<Long> groupDtos = groupService.findAllGroupsId(groupId);
            if (null == groupDtos || groupDtos.isEmpty()) {
                return Lists.newArrayList();
            }
            return groupDtos.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }, RequestUtil::getCurrentUser, "com.php25.qiuqiu.user.dao"));
        interceptor.addInnerInterceptor(dp);
        mybatisSqlSessionFactoryBean.setPlugins(interceptor);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(new SqlInjectorPlus());
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
        return mybatisSqlSessionFactoryBean.getObject();
    }
}


