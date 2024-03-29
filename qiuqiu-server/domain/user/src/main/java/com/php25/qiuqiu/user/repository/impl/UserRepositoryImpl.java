package com.php25.qiuqiu.user.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.dto.DataAccessLevel;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.TimeUtil;
import com.php25.qiuqiu.user.dao.UserDao;
import com.php25.qiuqiu.user.dao.UserRoleDao;
import com.php25.qiuqiu.user.dao.po.UserPo;
import com.php25.qiuqiu.user.dao.po.UserRolePo;
import com.php25.qiuqiu.user.dao.view.UserView;
import com.php25.qiuqiu.user.entity.User;
import com.php25.qiuqiu.user.entity.UserRole;
import com.php25.qiuqiu.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/2/3 14:51
 */
@RequiredArgsConstructor
@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final UserRoleDao userRoleDao;

    @Override
    public Optional<User> findByUsername(String username) {
        LambdaQueryWrapper<UserPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserPo::getUsername, username);
        UserPo userPo = userDao.selectOne(lambdaQueryWrapper);
        if (null == userPo) {
            return Optional.empty();
        }
        return Optional.of(toUser(userPo));
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        LambdaQueryWrapper<UserPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(UserPo::getUsername, username)
                .eq(UserPo::getPassword, password);
        UserPo userPo = userDao.selectOne(lambdaQueryWrapper);
        if (null == userPo) {
            return Optional.empty();
        }
        return Optional.of(toUser(userPo));
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<UserRolePo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(UserRolePo::getUserId, userId);
        List<UserRolePo> roleRefs = userRoleDao.selectList(lambdaQueryWrapper);
        if (null != roleRefs && !roleRefs.isEmpty()) {
            return roleRefs.stream().map(UserRolePo::getRoleId).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public boolean createRoleRefs(List<UserRole> roleRefs) {
        List<UserRolePo> userRolePoList = roleRefs.stream().map(userRole -> {
            UserRolePo userRolePo = new UserRolePo();
            BeanUtils.copyProperties(userRole, userRolePo);
            return userRolePo;
        }).collect(Collectors.toList());
//        userRoleDao.insertBatch(userRolePoList);
        userRoleDao.insertBatchSomeColumn(userRolePoList);
        return true;
    }

    @Override
    public boolean deleteRoleRefsByUserId(Long userId) {
        LambdaQueryWrapper<UserRolePo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(UserRolePo::getUserId, userId);
        userRoleDao.delete(lambdaQueryWrapper);
        return true;
    }

    @Override
    public List<Long> findUserIdsByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<UserRolePo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserRolePo::getRoleId, roleIds);
        List<UserRolePo> roleRefs = userRoleDao.selectList(lambdaQueryWrapper);
        if (null != roleRefs && !roleRefs.isEmpty()) {
            return roleRefs.stream().map(UserRolePo::getUserId).distinct().collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public Long countByGroupId(Long groupId) {
        LambdaQueryWrapper<UserPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserPo::getGroupId, groupId);
        Long count = userDao.selectCount(lambdaQueryWrapper);
        return count;
    }

    @Override
    public boolean save(User user, boolean isInsert) {
        UserPo userPo = fromUser(user);
        if (isInsert) {
            //新增
            userPo.setDataAccessLevel(DataAccessLevel.GROUP_AND_CHILDREN_DATA.name());
            int res = userDao.insert(userPo);
            user.setId(userPo.getId());
            return res>0;
        } else {
            //更新
            return userDao.updateById(userPo) > 0;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        UserPo userPo = userDao.selectById(id);
        if (null != userPo) {
            return Optional.of(toUser(userPo));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id) > 0;
    }

    @Override
    public IPage<User> page(String username, Integer pageNum, Integer pageSize) {
        IPage<UserView> page = userDao.selectPageByUsername(new Page<>(pageNum, pageSize),username);
        Page<User> userPage = new Page<>();
        userPage.setRecords(page.getRecords().stream().map(this::toUser0).collect(Collectors.toList()));
        userPage.setCurrent(pageNum);
        userPage.setTotal(page.getTotal());
        userPage.setSize(pageSize);
        return userPage;
    }

    private User toUser(UserPo userPo) {
        User user = new User();
        BeanUtils.copyProperties(userPo, user);
        if(null != userPo.getCreateTime()) {
            user.setCreateTime(TimeUtil.toLocalDateTime(userPo.getCreateTime()));
        }
        if (null != userPo.getUpdateTime()) {
            user.setLastModifiedTime(TimeUtil.toLocalDateTime(userPo.getUpdateTime()));
        }
        return user;
    }

    private User toUser0(UserView userView) {
        User user = new User();
        BeanUtils.copyProperties(userView, user);
        if(null != userView.getCreateTime()) {
            user.setCreateTime(TimeUtil.toLocalDateTime(userView.getCreateTime()));
        }
        if (null != userView.getUpdateTime()) {
            user.setLastModifiedTime(TimeUtil.toLocalDateTime(userView.getUpdateTime()));
        }
        return user;
    }

    private UserPo fromUser(User user) {
        UserPo userPo = new UserPo();
        BeanUtils.copyProperties(user, userPo);
        if (null != user.getCreateTime()) {
            userPo.setCreateTime(Date.from(user.getCreateTime()
                    .toInstant(ZoneOffset.ofHours(8))));
        }
        if (null != user.getLastModifiedTime()) {
            userPo.setUpdateTime(Date.from(user.getLastModifiedTime()
                    .toInstant(ZoneOffset.ofHours(8))));
        }
        return userPo;
    }
}
