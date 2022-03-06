package com.php25.qiuqiu.user.repository;

import com.php25.qiuqiu.user.entity.Group;
import com.php25.qiuqiu.user.entity.Permission;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/5 17:11
 */
public interface GroupRepository {

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


    /**
     * 根据组id获取组
     *
     * @param groupId 组id
     * @return 组
     */
    Optional<Group> findById(Long groupId);

    /**
     * 保存
     *
     * @param group    用户组
     * @param isInsert true:新增 false:更新
     * @return true:成功
     */
    boolean save(Group group, boolean isInsert);

    /**
     * 获取所有的用户组
     *
     * @return 用户组列表
     */
    List<Group> findAll();


    /**
     * 物理删除用户组
     *
     * @param id 用户组
     * @return true:成功
     */
    boolean deleteById(Long id);
}
