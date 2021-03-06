package com.php25.qiuqiu.user.service.impl;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.tree.TreeNode;
import com.php25.common.core.tree.Trees;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.group.GroupCreateDto;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.model.Group;
import com.php25.qiuqiu.user.repository.GroupRepository;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/6 15:49
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    @Override
    public TreeNode<GroupDto> getAllGroupTree() {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        List<GroupDto> groupDtoList = groups.stream().map(group -> {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group, groupDto);
            return groupDto;
        }).collect(Collectors.toList());
        return Trees.buildTree(groupDtoList);
    }

    @Override
    public List<GroupDto> childGroups(Long groupId) {
        List<Group> groups = groupRepository.findDirectGroupByParentId(groupId);
        return groups.stream().map(group -> {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group, groupDto);
            return groupDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean create(GroupCreateDto group) {
        Group group0 = new Group();
        BeanUtils.copyProperties(group, group0);
        group0.setEnable(true);
        group0.setIsNew(true);
        groupRepository.save(group0);
        return true;
    }

    @Override
    public Boolean update(GroupDto group) {
        Group group0 = new Group();
        BeanUtils.copyProperties(group, group0);
        group0.setIsNew(false);
        groupRepository.save(group0);
        return true;
    }

    @Override
    public Boolean delete(Long groupId) {
        Long count0 = groupRepository.countByParentId(groupId);
        if (null != count0 && count0 > 0) {
            throw Exceptions.throwBusinessException(UserErrorCode.GROUP_HAS_BEEN_REFERENCED_BY_GROUP);
        }

        Long count = userRepository.countByGroupId(groupId);
        if (null != count && count > 0) {
            throw Exceptions.throwBusinessException(UserErrorCode.GROUP_HAS_BEEN_REFERENCED_BY_USER);
        }
        groupRepository.deleteById(groupId);
        return true;
    }
}
