package com.php25.qiuqiu.monitor.service;

import com.php25.qiuqiu.monitor.dto.DictDto;

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
     * 新增字典记录
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
}
