package com.php25.qiuqiu.user.repository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.model.Resource;
import com.php25.qiuqiu.user.model.ResourcePermission;
import com.php25.qiuqiu.user.model.RoleResourcePermission;
import com.php25.qiuqiu.user.repository.ResourceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/25 21:03
 */
@Repository
public class ResourceRepositoryImpl extends BaseDbRepositoryImpl<Resource, String> implements ResourceRepository {

    public ResourceRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public boolean deleteResourcePermissions(String resourceName) {
        SqlParams sqlParams = Queries.of(dbType).from(ResourcePermission.class).whereEq("resource", resourceName).delete();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).delete(sqlParams) > 0;
    }

    @Override
    public boolean createResourcePermissions(List<ResourcePermission> resourcePermissions) {
        SqlParams sqlParams = Queries.of(dbType).from(ResourcePermission.class).insertBatch(resourcePermissions);
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).insertBatch(sqlParams);
        return true;
    }

    @Override
    public boolean hasReferencedByRole(String resourceName) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleResourcePermission.class).whereEq("resource", resourceName).count();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).count(sqlParams) > 0;
    }

    @Override
    public List<ResourcePermission> getAllResourcePermissions() {
        SqlParams sqlParams = Queries.of(dbType).from(ResourcePermission.class).select();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
    }

    @Override
    public List<ResourcePermission> getResourcePermissionsByResourceName(String resourceName) {
        SqlParams sqlParams = Queries.of(dbType).from(ResourcePermission.class).whereEq("resource",resourceName).select();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
    }
}
