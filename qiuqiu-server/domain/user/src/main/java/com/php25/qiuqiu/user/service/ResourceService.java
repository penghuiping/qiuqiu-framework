package com.php25.qiuqiu.user.service;

import com.php25.common.core.dto.PageDto;
import com.php25.qiuqiu.user.dto.resource.ResourceCreateDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDetailDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDto;
import com.php25.qiuqiu.user.dto.resource.ResourceUpdateDto;

import java.util.List;

/**
 * 资源相关操作
 *
 * @author penghuiping
 * @date 2021/3/25 16:45
 */
public interface ResourceService {

    /**
     * 创建资源
     *
     * @param resource 资源
     * @return true:创建成功
     */
    Boolean create(ResourceCreateDto resource);

    /**
     * 更新资源
     *
     * @param resource 资源
     * @return true:更新成功
     */
    Boolean update(ResourceUpdateDto resource);

    /**
     * 物理删除资源
     *
     * @param resourceName 资源名
     * @return true:删除成功
     */
    Boolean delete(String resourceName);

    /**
     * 分页查询资源列表
     *
     * @param resourceName 资源名
     * @param pageNum      当前页
     * @param pageSize     每页多少条
     * @return 资源列表
     */
    PageDto<ResourceDto> page(String resourceName, Integer pageNum, Integer pageSize);

    /**
     * 获取系统所有资源
     *
     * @return 系统所有资源
     */
    List<ResourceDetailDto> getAll();

    /**
     * 获取资源详情
     *
     * @param resourceName 资源名
     * @return 资源详情
     */
    ResourceDetailDto detail(String resourceName);
}
