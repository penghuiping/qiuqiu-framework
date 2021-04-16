package com.php25.qiuqiu.user.service.impl;

import com.google.common.collect.Lists;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.tree.TreeNode;
import com.php25.common.core.tree.Trees;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.qiuqiu.user.constant.DataAccessLevel;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.group.GroupCreateDto;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserSessionDto;
import com.php25.qiuqiu.user.model.Group;
import com.php25.qiuqiu.user.model.User;
import com.php25.qiuqiu.user.repository.GroupRepository;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.service.GroupService;
import com.php25.qiuqiu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    private final UserService userService;


    private GroupDto findById(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(groupOptional.get(), groupDto);
            return groupDto;
        }
        return null;
    }

    @Override
    public Long findGroupId(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getGroupId();
        }
        return null;
    }

    @Override
    public List<Long> findGroupsId(String username) {
        Long groupId = findGroupId(username);
        List<Group> groups = groupRepository.findEnabledGroupsByIds(Lists.newArrayList(groupId));
        List<GroupDto> groupDtos = groups.stream().map(group -> {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group,groupDto);
            return groupDto;
        }).collect(Collectors.toList());
        TreeNode<GroupDto> groupTree = this.getAllGroupTree();

        Set<Long> res  = new HashSet<>();
        for(GroupDto groupDto:groupDtos) {
            List<GroupDto> group0s = Trees.getAllSuccessorNodes(groupTree, groupDto);
            List<Long> tmp = group0s.stream().map(GroupDto::getId).collect(Collectors.toList());
            res.addAll(tmp);
        }
        return new ArrayList<>(res);
    }

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

    @Override
    public SearchParamBuilder getDataAccessScope(String username) {
        UserDto userDto = userService.getUserInfo(username);
        DataAccessLevel dataAccessLevel = userDto.getDataAccessLevel();
        SearchParamBuilder searchParamBuilder = new SearchParamBuilder();
        switch (dataAccessLevel) {
            case GLOBAL_DATA: {
                //查询全部,所以不需要搜索条件
                break;
            }
            case GROUP_DATA: {
                //只能查询当前组
                Long groupId = this.findGroupId(username);
                searchParamBuilder.append(SearchParam.of("groupId", Operator.EQ, groupId));
                break;
            }
            case GROUP_AND_CHILDREN_DATA: {
                //只能查询当前组与子组
                List<Long> groupIds = this.findGroupsId(username);
                searchParamBuilder.append(SearchParam.of("groupId", Operator.IN, groupIds));
                break;
            }
            case ONLY_SELF: {
                //只能访问自己创建的数据
                searchParamBuilder.append(SearchParam.of("createBy", Operator.EQ, username));
                break;
            }
            default: {
                //默认 ONLY_SELF
                searchParamBuilder.append(SearchParam.of("createBy", Operator.EQ, username));
                break;
            }
        }
        return searchParamBuilder;
    }
}
