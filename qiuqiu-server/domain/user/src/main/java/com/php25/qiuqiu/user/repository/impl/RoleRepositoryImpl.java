package com.php25.qiuqiu.user.repository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.entity.Role;
import com.php25.qiuqiu.user.entity.RoleResourcePermission;
import com.php25.qiuqiu.user.repository.RoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 16:55
 */
@Component
public class RoleRepositoryImpl extends BaseDbRepositoryImpl<Role, Long> implements RoleRepository {

    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public List<RoleResourcePermission> getPermissionsByRoleId(Long roleId) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleResourcePermission.class).whereEq("roleId", roleId).select();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
    }

    @Override
    public List<RoleResourcePermission> getPermissionsByRoleIds(List<Long> roleIds) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleResourcePermission.class).whereIn("roleId", roleIds).select();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
    }

    @Override
    public boolean createPermissionRefs(List<RoleResourcePermission> permissionRefs) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleResourcePermission.class).insertBatch(permissionRefs);
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).insertBatch(sqlParams);
        return true;
    }

    @Override
    public boolean deletePermissionRefsByRoleId(Long roleId) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleResourcePermission.class).whereEq("roleId", roleId).delete();
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).delete(sqlParams);
        return true;
    }

    @Override
    public boolean deletePermissionRefsByRoleIds(List<Long> roleIds) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleResourcePermission.class).whereIn("roleId", roleIds).delete();
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).delete(sqlParams);
        return true;
    }
}
