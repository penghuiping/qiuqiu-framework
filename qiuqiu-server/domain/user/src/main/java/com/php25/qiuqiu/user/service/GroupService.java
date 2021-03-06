package com.php25.qiuqiu.user.service;

import com.php25.common.core.tree.TreeNode;
import com.php25.qiuqiu.user.dto.group.GroupCreateDto;
import com.php25.qiuqiu.user.dto.group.GroupDto;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 21:44
 */
public interface GroupService {

    /**
     * 获取整棵组织树
     *
     * @return 组织数
     */
    TreeNode<GroupDto> getAllGroupTree();

    /**
     * 获取某个组织下的所有直接子组织
     *
     * @param groupId 组织id
     * @return 子组织列表
     */
    List<GroupDto> childGroups(Long groupId);

    /**
     * 新增组织
     *
     * @param group 组织
     * @return true:新建成功
     */
    Boolean create(GroupCreateDto group);

    /**
     * 更新组织
     *
     * @param group 组织
     * @return true:更新成功
     */
    Boolean update(GroupDto group);

    /**
     * 物理删除组织
     *
     * @param groupId 组织id
     * @return true:删除成功
     */
    Boolean delete(Long groupId);
}
