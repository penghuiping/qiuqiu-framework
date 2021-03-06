package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.model.Group;

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
}
