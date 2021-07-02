package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.entity.Group;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 17:11
 */
public interface GroupRepository extends BaseDbRepository<Group, Long> {

    /**
     * 通过父id查询直接子节点列表
     *
     * @param parentId 父id
     * @return 子节点类别
     */
    List<Group> findDirectGroupByParentId(Long parentId);

    /**
     * 通过父id查询直接子节点数量
     *
     * @param parentId 父id
     * @return 子节点数量
     */
    Long countByParentId(Long parentId);

    /**
     * 通过组id列表获取有效的组信息
     *
     * @param groupIds 组id列表
     * @return 组列表信息
     */
    List<Group> findEnabledGroupsByIds(List<Long> groupIds);
}
