package com.php25.qiuqiu.admin.config;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.php25.common.core.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2022/3/19 21:21
 */
@Slf4j
@Configuration
public class DbShardingConfig {

    @Primary
    @Bean
    public DataSource rwDatasource(@Qualifier("hikariDataSource") DataSource master) throws SQLException {
        Map<String, DataSource> map = new HashMap<>(16);
        map.put("ds_master", master);

        // 配置t_job表规则
        TableRuleConfiguration jobTableRuleConfig = new TableRuleConfiguration("t_audit_log", "ds_master.t_audit_log_${2022..2023}");

        //分表策略
        jobTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time",
                (PreciseShardingAlgorithm<Date>) (collection, preciseShardingValue) -> {
                    LocalDateTime localDateTime = TimeUtil.toLocalDateTime(preciseShardingValue.getValue());
                    String year = localDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
                    Optional<String> resultOptional = collection.stream().filter(tableName -> tableName.endsWith(year)).findFirst();
                    return resultOptional.orElse(null);
                }, (RangeShardingAlgorithm<Date>) (collection, rangeShardingValue) -> {
            Range<Date> range = rangeShardingValue.getValueRange();
            BoundType lowerBoundType = range.lowerBoundType();
            BoundType upperBoundType = range.upperBoundType();
            LocalDateTime lower = TimeUtil.toLocalDateTime(range.lowerEndpoint());
            LocalDateTime upper = TimeUtil.toLocalDateTime(range.upperEndpoint());

            List<String> result = collection.stream().filter(tableName -> {
                int year = Integer.parseInt(tableName.substring(tableName.lastIndexOf("_") + 1));
                int lowerYear = lower.getYear();
                int upperYear = upper.getYear();
                if (lowerYear < year && year < upperYear) {
                    return true;
                }

                if (BoundType.CLOSED.equals(lowerBoundType)) {
                    if (lowerYear == year) {
                        return true;
                    }
                }

                if (BoundType.CLOSED.equals(upperBoundType)) {
                    if (upperYear == year) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            log.info("result:{}", result);
            return result;
        }));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(jobTableRuleConfig);
        // 获取数据源对象
        return ShardingDataSourceFactory.createDataSource(map, shardingRuleConfig,new Properties() );
    }

    @Primary
    @Bean
    public DataSourceHealthIndicator dataSourceHealthIndicator(DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource, "select 'x' from dual");
    }

}
