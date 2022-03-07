package com.php25.qiuqiu.user.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.TimeUtil;
import com.php25.qiuqiu.user.dao.UserDao;
import com.php25.qiuqiu.user.dao.UserRoleDao;
import com.php25.qiuqiu.user.dao.po.UserPo;
import com.php25.qiuqiu.user.dao.po.UserRolePo;
import com.php25.qiuqiu.user.entity.User;
import com.php25.qiuqiu.user.entity.UserRole;
import com.php25.qiuqiu.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
        User user = new User();
        BeanUtils.copyProperties(userPo, user);
        return Optional.of(user);
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
        User user = new User();
        BeanUtils.copyProperties(userPo, user);
        return Optional.of(user);
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
        userRoleDao.insertBatch(userRolePoList);
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
        UserPo userPo = new UserPo();
        BeanUtils.copyProperties(userPo, user);
        if (isInsert) {
            //新增
            return userDao.insert(userPo) > 0;
        } else {
            //更新
            return userDao.updateById(userPo) > 0;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        UserPo userPo = userDao.selectById(id);
        if (null != userPo) {
            User user = new User();
            BeanUtils.copyProperties(userPo, user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id) > 0;
    }

    @Override
    public IPage<User> page(String username, Integer pageNum, Integer pageSize) {
        IPage<UserPo> page = userDao.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<UserPo>lambdaQuery().eq(StringUtil.isNotBlank(username),UserPo::getUsername, username));
        Page<User> userPage = new Page<>();
        userPage.setRecords(page.getRecords().stream().map(userPo -> {
            User user = new User();
            BeanUtils.copyProperties(userPo, user);
            user.setCreateTime(TimeUtil.toLocalDateTime(userPo.getCreateTime()));
            user.setLastModifiedTime(TimeUtil.toLocalDateTime(userPo.getLastModifiedTime()));
            return user;
        }).collect(Collectors.toList()));
        userPage.setCurrent(pageNum);
        userPage.setTotal(page.getTotal());
        userPage.setSize(pageSize);
        return userPage;
    }
}
