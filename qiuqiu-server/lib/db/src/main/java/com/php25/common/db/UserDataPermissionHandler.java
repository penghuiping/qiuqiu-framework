package com.php25.common.db;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.php25.common.core.dto.CurrentUser;
import com.php25.common.core.dto.CurrentUserDto;
import com.php25.common.core.dto.DataAccessLevel;
import com.php25.common.core.util.StringUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author penghuiping
 * @date 2023/9/9 19:32
 */
public class UserDataPermissionHandler implements DataPermissionHandler {
    private static final Logger log = LoggerFactory.getLogger(UserDataPermissionHandler.class);
    private final Set<String> daoMethodNames;

    private final FindAllSubgroupStrategy findAllSubgroupStrategy;
    private final FindCurrentUserStrategy findCurrentUserStrategy;

    public UserDataPermissionHandler(FindAllSubgroupStrategy findAllSubgroupStrategy, FindCurrentUserStrategy findCurrentUserStrategy, String... scanPackages) {
        List<Method> methods = FindAnnotationMethodUtil.find(DataPermission.class, scanPackages);
        this.daoMethodNames = new HashSet<>();
        for (Method method : methods) {
            this.daoMethodNames.add(method.getDeclaringClass().getName() + "." + method.getName());
        }
        this.findAllSubgroupStrategy = findAllSubgroupStrategy;
        this.findCurrentUserStrategy = findCurrentUserStrategy;
    }

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        if (!daoMethodNames.contains(mappedStatementId)) {
            return where;
        }

        CurrentUser currentUser = findCurrentUserStrategy.find();
        if (null == currentUser) {
            return where;
        }

        CurrentUserDto currentUserDto = (CurrentUserDto) currentUser;
        String groupCode = currentUserDto.getGroupCode();
        if (StringUtil.isBlank(groupCode)) {
            return where;
        }

        if (DataAccessLevel.GROUP_DATA.equals(currentUserDto.getDataAccessLevel())) {
            Expression exp = new EqualsTo(new Column(Constants.GROUP_ID),
                    new StringValue(groupCode));
            return where == null ? exp : new AndExpression(where, exp);
        }

        if (DataAccessLevel.GROUP_AND_CHILDREN_DATA.equals(currentUserDto.getDataAccessLevel())) {
            List<String> groupIds = findAllSubgroupStrategy.find(groupCode);
            ExpressionList items = new ExpressionList();
            for (String groupId : groupIds) {
                items.addExpressions(new StringValue(groupId));
            }
            InExpression exp = new InExpression(new Column(Constants.GROUP_ID), items);
            return where == null ? exp : new AndExpression(where, exp);
        }

        if (DataAccessLevel.ONLY_SELF.equals(currentUserDto.getDataAccessLevel())) {
            Expression exp = new EqualsTo(new Column(Constants.CREATE_USER), new StringValue(groupCode));
            return null == where ? exp : new AndExpression(where, exp);
        }

        return where;
    }
}
