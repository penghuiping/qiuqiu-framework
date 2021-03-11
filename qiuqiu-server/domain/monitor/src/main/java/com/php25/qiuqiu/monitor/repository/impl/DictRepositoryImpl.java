package com.php25.qiuqiu.monitor.repository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.monitor.model.Dict;
import com.php25.qiuqiu.monitor.repository.DictRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/11 15:09
 */
@Repository
public class DictRepositoryImpl extends BaseDbRepositoryImpl<Dict, Long> implements DictRepository {

    public DictRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public Optional<Dict> findByKey(String key) {
        SqlParams sqlParams = Queries.of(dbType).from(Dict.class).whereEq("key", key).single();
        Dict dict = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
        if (null == dict) {
            return Optional.empty();
        }
        return Optional.of(dict);
    }

    @Override
    public Boolean deleteByKey(String key) {
        SqlParams sqlParams = Queries.of(dbType).from(Dict.class).whereEq("key", key).delete();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).delete(sqlParams) > 0;
    }
}
