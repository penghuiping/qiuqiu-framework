package com.php25.qiuqiu.user.repository.impl;

import com.google.common.collect.Lists;
import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.model.Group;
import com.php25.qiuqiu.user.repository.GroupRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/5 17:12
 */
@Component
public class GroupRepositoryImpl extends BaseDbRepositoryImpl<Group, Long> implements GroupRepository {

    public GroupRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public List<Group> findDirectGroupByParentId(Long parentId) {
        SqlParams sqlParams = Queries.of(dbType).from(Group.class).whereEq("parentId", parentId).select();
        List<Group> groups = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
        if (null != groups && !groups.isEmpty()) {
            return groups;
        }
        return Lists.newArrayList();
    }

    @Override
    public Long countByParentId(Long parentId) {
        SqlParams sqlParams = Queries.of(dbType).from(Group.class).whereEq("parentId", parentId).count();
        Long res = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).count(sqlParams);
        if (null != res) {
            return res;
        }
        return -1L;
    }

    @Override
    public List<Group> findEnabledGroupsByIds(List<Long> groupIds) {
        if (groupIds.size() == 1) {
            Optional<Group> groupOptional = this.findByIdEnable(groupIds.get(0));
            if (groupOptional.isPresent()) {
                return Lists.newArrayList(groupOptional.get());
            }
        } else {
            SqlParams sqlParams = Queries.of(dbType).from(Group.class).whereIn("groupId", groupIds).select();
            return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
        }
        return Lists.newArrayList();
    }
}
