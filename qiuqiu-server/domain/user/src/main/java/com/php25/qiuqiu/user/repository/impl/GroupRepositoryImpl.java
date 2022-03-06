package com.php25.qiuqiu.user.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.php25.qiuqiu.user.dao.GroupDao;
import com.php25.qiuqiu.user.dao.po.GroupPo;
import com.php25.qiuqiu.user.entity.Group;
import com.php25.qiuqiu.user.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/5 17:12
 */
@RequiredArgsConstructor
@Component
public class GroupRepositoryImpl implements GroupRepository {
    private final GroupDao groupDao;

    @Override
    public List<Group> findDirectGroupByParentId(Long parentId) {
        LambdaQueryWrapper<GroupPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GroupPo::getParentId, parentId);
        List<GroupPo> groupPos = groupDao.selectList(lambdaQueryWrapper);
        if (null != groupPos && !groupPos.isEmpty()) {
            return groupPos.stream().map(groupPo -> {
                Group group = new Group();
                BeanUtils.copyProperties(groupPo, group);
                return group;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public Long countByParentId(Long parentId) {
        LambdaQueryWrapper<GroupPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GroupPo::getParentId, parentId);
        Long res = groupDao.selectCount(lambdaQueryWrapper);
        if (null != res) {
            return res;
        }
        return -1L;
    }

    @Override
    public List<Group> findEnabledGroupsByIds(List<Long> groupIds) {
        List<GroupPo> groupPos = null;
        if (groupIds.size() == 1) {
            LambdaQueryWrapper<GroupPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(GroupPo::getId, groupIds.get(0));
            groupPos = groupDao.selectList(lambdaQueryWrapper);
        } else {
            LambdaQueryWrapper<GroupPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(GroupPo::getId, groupIds);
            groupPos = groupDao.selectList(lambdaQueryWrapper);
        }
        if (null != groupPos && !groupPos.isEmpty()) {
            return groupPos.stream().map(groupPo -> {
                Group group = new Group();
                BeanUtils.copyProperties(groupPo, group);
                return group;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public Optional<Group> findById(Long groupId) {
        GroupPo groupPo = groupDao.selectById(groupId);
        if (null != groupPo) {
            Group group = new Group();
            BeanUtils.copyProperties(groupPo, group);
            return Optional.of(group);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Group group, boolean isInsert) {
        GroupPo groupPo = new GroupPo();
        BeanUtils.copyProperties(group, groupPo);
        return groupDao.insert(groupPo) > 0;
    }

    @Override
    public List<Group> findAll() {
        List<GroupPo> groupPos = groupDao.selectList(Wrappers.lambdaQuery());
        if (null != groupPos && !groupPos.isEmpty()) {
            return groupPos.stream().map(groupPo -> {
                Group group = new Group();
                BeanUtils.copyProperties(groupPo, group);
                return group;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public boolean deleteById(Long id) {
        return groupDao.deleteById(id) > 0;
    }
}
