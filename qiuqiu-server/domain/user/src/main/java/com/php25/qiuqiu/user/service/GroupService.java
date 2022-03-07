package com.php25.qiuqiu.user.service;

import com.php25.common.core.specification.SearchParamBuilder;
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
     * 通过username获取用户对应组及子组id列表
     *
     * @param username 用户名
     * @return 用户对应组及子组id列表
     */
    List<Long> findGroupsId(String username);

    /**
     * 通过username获取用户对应组id
     *
     * @param username 用户名
     * @return 组id
     */
    Long findGroupId(String username);

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

    /**
     *  根据用户名构建对应词用户的数据访问范围查询条件
     *
     * @param username 用户名
     * @return 此用户的对应数据访问范围查询条件
     */
    SearchParamBuilder getDataAccessScope(String username);
}
