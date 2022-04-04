package com.php25.qiuqiu.monitor.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.qiuqiu.monitor.dto.DictDto;

import java.util.List;

/**
 * 系统数据字典配置服务
 * 可以通过后台直接刷新运行时系统配置参数，达到系统开关的目的
 *
 * @author penghuiping
 * @date 2021/3/1 20:47
 */
public interface DictionaryService {

    /**
     * 根据key获取值
     * 1. 内存中有先从内存中取
     * 2. 内存中没有从数据库中取
     * 3. 从数据库中取完，设置内存值
     *
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 更新某条字典记录(注: 此操作只会操作数据库，不会更新缓存)
     *
     * @param dictDto 字典记录
     * @return true:更新成功
     */
    Boolean update(DictDto dictDto);

    /**
     * 删除数据字典记录(注: 此操作只会操作数据库，不会更新缓存)
     *
     * @param key 键
     * @return true:删除成功
     */
    Boolean delete(String key);

    /**
     * 新增字典记录(注: 此操作只会操作数据库，不会更新缓存)
     *
     * @param key         字典键
     * @param value       字典值
     * @param description 字典描述
     * @return true: 创建成功
     */
    Boolean create(String key, String value, String description);

    /**
     * 移除缓存(注:此接口支持移除系统中所有模块副本缓存)
     *
     * @param key 键
     * @return true: 移除成功
     */
    Boolean removeCache(String key);

    /**
     * 分页查询
     *
     * @param key      通过key搜索
     * @param pageNum  页码
     * @param pageSize 每页几条记录
     * @return 分页信息
     */
    DataGridPageDto<DictDto> page(String key, Integer pageNum, Integer pageSize);

    /**
     * 获取系统启动所需要初始化配置项
     * @return 系统初始化配置项
     */
    List<DictDto> getAllInitConfig();
}
