package com.php25.qiuqiu.user.repository.impl;

import com.google.common.collect.Lists;
import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.model.Permission;
import com.php25.qiuqiu.user.model.PermissionRef;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/5 17:41
 */
@Component
public class PermissionRepositoryImpl extends BaseDbRepositoryImpl<Permission, Long> implements PermissionRepository {

    public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public List<Long> getRoleIdsByPermissionIds(List<Long> permissionIds) {
        SqlParams sqlParams = Queries.of(dbType).from(PermissionRef.class)
                .whereIn("permissionId", permissionIds)
                .select();
        List<PermissionRef> list = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
        if (null != list && !list.isEmpty()) {
            return list.stream().map(PermissionRef::getRoleId).distinct().collect(Collectors.toList());
        } else {
            return Lists.newArrayList();
        }
    }
}