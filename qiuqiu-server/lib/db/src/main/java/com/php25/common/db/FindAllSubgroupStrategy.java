package com.php25.common.db;

import java.util.List;

/**
 * @author penghuiping
 * @date 2023/9/9 21:59
 */
public interface FindAllSubgroupStrategy {

    /**
     * 根据组织id获取所有下级组织id
     * @param groupId 组织id
     * @return 所有下级组织id
     */
    List<String> find(String groupId);
}
